package com.testcontainer.dynamo.repository;

import java.util.Arrays;

import org.hamcrest.core.IsNull;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.testcontainer.dynamo.model.Movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableScan
@Repository
@RequiredArgsConstructor
@Slf4j
public class MoviesRepository {

	private static String TABLE_NAME = "Movies";
	
	private final DynamoDBMapper mapper;
	private final DynamoDB dynamoDb;
	
	public void save(Movie movie) {
		this.existOrCreateTable();
		log.info("Saveing... {}", movie);
		this.mapper.save(movie);
	}
	
	public Movie findByYearAndTitle(Integer year, String title) {
		log.info("Loading movie from year {} and title {}", year, title);
		return mapper.load(Movie.class, year, title);
	}
	
	private void existOrCreateTable() {
		var table = dynamoDb.getTable(TABLE_NAME);
		if (table. == null) {
			this.createTable();
		}
	}
	
	private void createTable() {
		try {
			System.out.println("Attempting to create table; please wait...");
			Table table = dynamoDb.createTable(TABLE_NAME,
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
