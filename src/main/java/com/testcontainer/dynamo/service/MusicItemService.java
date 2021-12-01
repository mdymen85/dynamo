package com.testcontainer.dynamo.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.testcontainer.dynamo.model.Movie;
import com.testcontainer.dynamo.repository.MoviesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MusicItemService {

	private final DynamoDB dynamoDB;
	private final MoviesRepository moviesRepository;
	
	public Movie load(String artist, String songTitle) {
		
		List<Movie> movies = moviesRepository.findByYearAndTitle(2013, "Rush");
		
		
		return null;
		
	//	return mapper.load(MusicItem.class, 2009);
		
//		return mapper.load(MusicItem.builder()
//				.artist(artist)
//				.songTitle(songTitle)
//				.build());
		
		
	}
	
}
