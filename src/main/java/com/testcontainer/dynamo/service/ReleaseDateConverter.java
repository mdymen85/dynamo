package com.testcontainer.dynamo.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class ReleaseDateConverter implements DynamoDBTypeConverter<String, LocalDate>{

	@Override
	public String convert(LocalDate releaseDate) {
		return releaseDate.toString();
	}

	@Override
	public LocalDate unconvert(String releaseDate) {
		var localDateTime = LocalDateTime.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return localDateTime.toLocalDate();
	}

}
