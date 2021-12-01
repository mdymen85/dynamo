package com.testcontainer.dynamo.model;

import java.util.Map;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import lombok.Builder;
import lombok.Data;

@DynamoDBTable(tableName="Movies")
public class Movie {

	private String id;
	
	private Integer year;
	
	private String title;  
	
	private Info info;
    
	public Movie() {}
	
	@DynamoDBAutoGeneratedKey
	@DynamoDBHashKey
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBAttribute(attributeName="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@DynamoDBAttribute(attributeName="year")    
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

    @DynamoDBTypeConverted(converter = InfoConverter.class)
    @DynamoDBAttribute(attributeName = "info")
	public Info getInfo() {
		return info;
	}

	@DynamoDBAttribute(attributeName = "info")
	public void setInfo(Info info) {
		this.info = info;
	}

    
//    public Integer getYear() { 
//    	return year;
//    }
//    
//    public void setYear(Integer year) {
//    	this.year = year;
//    }
	
}
