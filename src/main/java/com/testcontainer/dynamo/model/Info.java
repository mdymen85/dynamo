package com.testcontainer.dynamo.model;

import java.time.LocalDate;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.testcontainer.dynamo.service.ReleaseDateConverter;

import lombok.Data;

@Data
@DynamoDBDocument
public class Info {

	private List<String> directors;
	@DynamoDBAttribute(attributeName = "release_date")
	@DynamoDBTypeConverted(converter = ReleaseDateConverter.class)
	private LocalDate releaseDate;
	private Double rating;	
	private List<String> genres;
	@DynamoDBAttribute(attributeName = "image_url")
	private String imageUrl;
	private String plot;
	@DynamoDBAttribute(attributeName = "running_time_secs")
	private Integer runningTimeSecs;
	private List<String> actors;
	
}
