package com.testcontainer.dynamo.service;

import org.springframework.stereotype.Component;
import com.testcontainer.dynamo.model.Movie;
import com.testcontainer.dynamo.repository.MoviesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieService {

	private final MoviesRepository moviesRepository;
	
	public void save(Movie movie) {
		this.moviesRepository.save(movie);
	}
	
	public Movie load(Integer year, String title) {
		return moviesRepository.findByYearAndTitle(year, title);
	}
	
}
