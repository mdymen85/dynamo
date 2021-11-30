package com.testcontainer.dynamo.service;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.testcontainer.dynamo.model.MusicItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MusicItemService {

	private final DynamoDBMapper mapper;
	
	public MusicItem load(String artist, String songTitle) {
		
		
		return mapper.load(MusicItem.class, 2009);
		
//		return mapper.load(MusicItem.builder()
//				.artist(artist)
//				.songTitle(songTitle)
//				.build());
		
		
	}
	
}
