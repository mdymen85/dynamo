package com.testcontainer.dynamo.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.testcontainer.dynamo.model.Movie;

@EnableScan
@Repository
public interface MoviesRepository extends CrudRepository<Movie, Long>  {

	public List<Movie> findByYearAndTitle(Integer year, String title);
	
}
