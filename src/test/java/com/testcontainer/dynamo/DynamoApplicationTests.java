package com.testcontainer.dynamo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class DynamoApplicationTests {

	@ClassRule
	public static GenericContainer dynamoDBLocal =
	    new GenericContainer("amazon/dynamodb-local:1.11.477")
	        .withExposedPorts(8000);
	
	@Test
	void contextLoads() {
        String tableHashKey = "id";
        String tableName = "test-table";

        String serviceEndpoint = buildEndpointUrl();
        AmazonDynamoDB client = createAmazonDynamoDBClient(serviceEndpoint);
        CreateTableResult table = createCreateDynamoDBTable(client, tableName, tableHashKey);

        log.info("Created table: {}", table.getTableDescription());
	}
	
    @NotNull
    private String buildEndpointUrl() {
        String serviceEndpoint = "http://localhost:";

        if (dynamoDBLocal.isRunning()) {
            serviceEndpoint += dynamoDBLocal.getFirstMappedPort();
        } else {
        	log.info(dynamoDBLocal.getLogs());
            throw new IllegalStateException("DynamoDB Local is not running.");
        }

//        logger.info("Using DynamoDB endpoint: {}", serviceEndpoint);

        return serviceEndpoint;
    }
    
    private AmazonDynamoDB createAmazonDynamoDBClient(String serviceEndpoint) {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, "us-east-1");

        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }
    
    private CreateTableResult createCreateDynamoDBTable(AmazonDynamoDB client, String tableName, String tableHashKey) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition(tableHashKey, ScalarAttributeType.S));

        List<KeySchemaElement> ks = new ArrayList<>();
        ks.add(new KeySchemaElement(tableHashKey, KeyType.HASH));

        ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput(10L, 10L);

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(ks)
                .withProvisionedThroughput(provisionedthroughput);

        return client.createTable(request);
    }


}
