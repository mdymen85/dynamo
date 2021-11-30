package com.testcontainer.dynamo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

@Configuration
public class DynamoConfig {

	@Bean
	public AmazonDynamoDB createConnection() {
		return AmazonDynamoDBClientBuilder.standard()
		.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
		.build();		
	}
	
	@Bean
	public DynamoDB createDynamoDB(AmazonDynamoDB client) {
		return new DynamoDB(client);
	}
	
	@Bean 
	public DynamoDBMapper createMapper(AmazonDynamoDB client) {
		return new DynamoDBMapper(client);
	}
	
}
