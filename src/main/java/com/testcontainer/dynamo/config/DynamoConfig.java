package com.testcontainer.dynamo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

@Configuration
@ComponentScan(basePackages = "com.testcontainer.dynamo.*")
@EnableDynamoDBRepositories(basePackages = {"com.testcontainer.dynamo.repository"})
public class DynamoConfig {

	@Bean(name = "amazonDynamoDB")
	public AmazonDynamoDB createConnection() {
		return AmazonDynamoDBClientBuilder.standard()
		.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
		.build();		
	}
	
	@Bean
	public DynamoDB createDynamoDB(AmazonDynamoDB client) {
		return new DynamoDB(client);
	}
	
//	@Bean 
//	public DynamoDBMapper createMapper(AmazonDynamoDB client) {
//		return new DynamoDBMapper(client);
//	}
//	
//	@Bean(name="entityManagerFactory")
//	public LocalSessionFactoryBean sessionFactory() {
//	    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//
//	    return sessionFactory;
//	} 
	
}
