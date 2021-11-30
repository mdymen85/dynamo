package com.testcontainer.dynamo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testcontainer.dynamo.dto.MusicItemDTO;
import com.testcontainer.dynamo.model.MusicItem;
import com.testcontainer.dynamo.service.MusicItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MusicItemController {

	private final MusicItemService musicService;
	
	@RequestMapping(path = "/api/v1/musicitem", method = RequestMethod.GET)
	public MusicItem getMusicItem(@RequestBody MusicItemDTO musicItemDTO) {
		return musicService.load(musicItemDTO.getArtist(), musicItemDTO.getSongTitle());
	}
	
}
