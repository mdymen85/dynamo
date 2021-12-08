package com.testcontainer.dynamo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReleaseDateConverter implements DynamoDBTypeConverter<String, LocalDate>{

	@Override
	public String convert(LocalDate releaseDate) {
		log.info("Converting {} to string.", releaseDate);
		return releaseDate.toString();
	}

	@Override
	public LocalDate unconvert(String releaseDate) {
		log.info("Converting string {} to LocalDate.", releaseDate);
		var localDateTime = LocalDateTime.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return localDateTime.toLocalDate();
	}

}
