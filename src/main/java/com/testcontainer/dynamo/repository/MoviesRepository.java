package com.testcontainer.dynamo.repository;

import java.util.Arrays;

import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.testcontainer.dynamo.model.Movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MoviesRepository {

	private static String TABLE_NAME = "Movies";
	
	private final DynamoDBMapper mapper;
	private final DynamoDB dynamoDb;
	
	public void save(Movie movie) {
		this.createIfNotExists();
		log.info("Saving... {}", movie);
		this.mapper.save(movie);
	}
	
	public Movie findByYearAndTitle(Integer year, String title) {
		log.info("Loading movie from year: {} and title: {}", year, title);
		return mapper.load(Movie.class, year, title);
	}
	
	private void createIfNotExists() {
		try {
			var table = dynamoDb.getTable(TABLE_NAME);
			table.describe();
		} catch (ResourceNotFoundException r) {
			this.createTable();
		}
	}
	
	private void createTable() {
		try {
			System.out.println("Attempting to create table; please wait...");
			Table table = dynamoDb.createTable(TABLE_NAME,
	          Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
	                                                                    // key
	              new KeySchemaElement("title", KeyType.RANGE)), // Sort key
	          Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
	              new AttributeDefinition("title", ScalarAttributeType.S)),
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
