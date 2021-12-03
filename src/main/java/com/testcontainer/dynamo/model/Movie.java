package com.testcontainer.dynamo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;

@DynamoDBTable(tableName="Movies")
@Data
public class Movie {

	@DynamoDBHashKey
	private Integer year;
	
	@DynamoDBRangeKey
	private String title;  
	
	private Info info;
	
}
