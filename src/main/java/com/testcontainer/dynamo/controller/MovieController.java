package com.testcontainer.dynamo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.internal.http.JsonErrorCodeParser;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.testcontainer.dynamo.dto.MovieDTO;
import com.testcontainer.dynamo.model.Movie;
import com.testcontainer.dynamo.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MovieController {

	private final MovieService movieService;
	private final ModelMapper modelMapper;
	
	@RequestMapping(path = "/api/v1/movie/{year}/{title}", method = RequestMethod.GET)
	public ResponseEntity<MovieDTO> getMusicItem(@PathVariable("year") Integer year, @PathVariable("title") String title) throws Exception {
		log.info("Received year {} and title {} to load the movie", year, title);
		
		var movie = movieService.load(year, title);
		
		log.info("Movie item loaded: {}", movie);
		
		var convertedMovie = modelMapper.map(movie, MovieDTO.class);
		
		return new ResponseEntity<>(convertedMovie, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/api/v1/movie", method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody MovieDTO movieDTO) {
		var movie = this.modelMapper.map(movieDTO, Movie.class);		
		log.info("Received request to save {}", movieDTO);
		this.movieService.save(movie);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
