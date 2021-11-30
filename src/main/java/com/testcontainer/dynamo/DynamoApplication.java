package com.testcontainer.dynamo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootApplication
public class DynamoApplication {

	public static void main(String[] args) throws IOException {
		

//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
//            .build();
//
//        DynamoDB dynamoDB = new DynamoDB(client);
//
//        String tableName = "Movies";
//
//        try {
//            System.out.println("Attempting to create table; please wait...");
//            Table table = dynamoDB.createTable(tableName,
//                Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
//                                                                          // key
//                    new KeySchemaElement("title", KeyType.RANGE)), // Sort key
//                Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
//                    new AttributeDefinition("title", ScalarAttributeType.S)),
//                new ProvisionedThroughput(10L, 10L));
//            table.waitForActive();
//            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
//
//        }
//        catch (Exception e) {
//            System.err.println("Unable to create table: ");
//            System.err.println(e.getMessage());
//        }

		
		
//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
//                .build();
//
//            DynamoDB dynamoDB = new DynamoDB(client);
//
//            Table table = dynamoDB.getTable("Movies");
//
//            JsonParser parser = new JsonFactory().createParser(new File("moviedata.json"));
//
//            JsonNode rootNode = new ObjectMapper().readTree(parser);
//            Iterator<JsonNode> iter = rootNode.iterator();
//
//            ObjectNode currentNode;
//
//            while (iter.hasNext()) {
//                currentNode = (ObjectNode) iter.next();
//
//                int year = currentNode.path("year").asInt();
//                String title = currentNode.path("title").asText();
//
//                try {
//                    table.putItem(new Item().withPrimaryKey("year", year, "title", title).withJSON("info",
//                        currentNode.path("info").toString()));
//                    System.out.println("PutItem succeeded: " + year + " " + title);
//
//                }
//                catch (Exception e) {
//                    System.err.println("Unable to add movie: " + year + " " + title);
//                    System.err.println(e.getMessage());
//                    break;
//                }
//            }
//            parser.close();
		
//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
//                .build();
//
//            DynamoDB dynamoDB = new DynamoDB(client);
//
//            Table table = dynamoDB.getTable("Movies");
//
//            int year = 2015;
//            String title = "The Big New Movie";
//
//            final Map<String, Object> infoMap = new HashMap<String, Object>();
//            infoMap.put("plot", "Nothing happens at all.");
//            infoMap.put("rating", 0);
//
//            try {
//                System.out.println("Adding a new item...");
//                PutItemOutcome outcome = table
//                    .putItem(new Item().withPrimaryKey("year", year, "title", title).withMap("info", infoMap));
//
//                System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
//
//            }
//            catch (Exception e) {
//                System.err.println("Unable to add item: " + year + " " + title);
//                System.err.println(e.getMessage());
//            }
		
		SpringApplication.run(DynamoApplication.class, args);
	}

}
