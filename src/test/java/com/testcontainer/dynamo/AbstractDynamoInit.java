package com.testcontainer.dynamo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class AbstractDynamoInit {

	protected static Integer PORT = 8000;
	
	@Autowired
    private DynamoDB dynamoDB;
    
    public void createTable(DynamoDB dynamoDB) {
		String tableName = "Test";

		try {
		  Table table = dynamoDB.createTable(tableName,
		      Arrays.asList(new KeySchemaElement("id", KeyType.HASH), // Partition
		                                                                // key
		          new KeySchemaElement("surname", KeyType.RANGE)), // Sort key
		      Arrays.asList(new AttributeDefinition("id", ScalarAttributeType.N),
		          new AttributeDefinition("surname", ScalarAttributeType.S)),
		      new ProvisionedThroughput(10L, 10L));
		  table.waitForActive();
		  System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

		}
		catch (Exception e) {
		  System.err.println("Unable to create table: ");
		  System.err.println(e.getMessage());
		}
    }
	
}
