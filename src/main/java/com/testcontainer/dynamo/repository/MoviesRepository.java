package com.testcontainer.dynamo.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.testcontainer.dynamo.model.Movie;

import lombok.RequiredArgsConstructor;

@EnableScan
@Repository
@RequiredArgsConstructor
public class MoviesRepository {

	private final DynamoDBMapper mapper;
	
	public void save(Movie movie) {
		this.mapper.save(movie);
	}
	
	public Movie findByYearAndTitle(Integer year, String title) {
		return mapper.load(Movie.class, year, title);
	}
	
}
