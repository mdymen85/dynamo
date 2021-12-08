package com.testcontainer.dynamo;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcontainer.dynamo.AbstractDynamoInit.InnerDynamoTestConfiguration;

import lombok.extern.slf4j.Slf4j;

@Testcontainers
@ContextConfiguration(classes = InnerDynamoTestConfiguration.class)
@Slf4j
public class AbstractDynamoInit {

	private static Integer PORT = 8000;
	private static String AVAILABILITY_ZONE = "us-west-2";
	
	@Container
	public static GenericContainer<?> dynamoDBLocal =
	    new GenericContainer<>("amazon/dynamodb-local:1.11.477")
	        .withExposedPorts(PORT);
	
	
	@Configuration
	@ComponentScan(basePackages = "com.testcontainer.dynamo.*")
	@Profile("test")
	static class InnerDynamoTestConfiguration {

		@Bean(name = "amazonDynamoDB")
		public AmazonDynamoDB createConnection() {		
			return AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder
					.EndpointConfiguration(generateUrl(), AVAILABILITY_ZONE))
			.build();		
		}
		
		private String generateUrl() {
			return new StringBuilder("http://localhost")
					.append(":")
					.append(dynamoDBLocal.getFirstMappedPort())
					.toString();
		}

		@Bean
		public DynamoDB createDynamoDB(AmazonDynamoDB client) {
			return new DynamoDB(client);
		}
		
		@Bean 
		public DynamoDBMapper createMapper(AmazonDynamoDB client) {
			return new DynamoDBMapper(client);
		}

		@Bean
		public ObjectMapper objectMapper() {
			return new ObjectMapper();
		}
		
		@Bean
		public ModelMapper modelMapper() {
			return new ModelMapper();
		}
		
	}	
	
	@Autowired
    private DynamoDB dynamoDB;
    
    public void createTable() {

	      String tableName = "Movies";

	      try {
	          log.info("Attempting to create table; please wait...");
	          Table table = dynamoDB.createTable(tableName,
	              Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
	                                                                        // key
	                  new KeySchemaElement("title", KeyType.RANGE)), // Sort key
	              Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
	                  new AttributeDefinition("title", ScalarAttributeType.S)),
	              new ProvisionedThroughput(10L, 10L));
	          table.waitForActive();
	          log.info("Success.  Table status: " + table.getDescription().getTableStatus());

	      }
	      catch (Exception e) {
	          log.info("Unable to create table: ");
	          System.err.println(e.getMessage());
	      }
    }
	
}
