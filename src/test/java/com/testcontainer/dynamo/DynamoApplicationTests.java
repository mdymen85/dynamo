package com.testcontainer.dynamo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcontainer.dynamo.dto.MovieDTO;
import com.testcontainer.dynamo.model.Movie;
import com.testcontainer.dynamo.service.MovieService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Need to run with profile: test
 * -Dspring.active.profile=test
 * 
 * @author Martin Dymenstein
 *
 */
@SpringBootTest
@Slf4j
class DynamoApplicationTests extends AbstractDynamoInit {
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private ModelMapper modelMapper;

	
	
	@BeforeAll
	public static void init() {
		dynamoDBLocal.start();
	}
	
	@Test
	void createMovie() throws JsonMappingException, JsonProcessingException {
        this.createTable();
        
        String jsonObject = "{\n"
				+ "    \"year\":\"2023\",\n"
				+ "    \"title\":\"Movie title\",\n"
				+ "    \"info\": {\n"
				+ "        \"directors\": [\"Michael Perez\", \"Carl Harris\"],\n"
				+ "        \"release_date\": \"2013-09-02T00:00:00Z\",\n"
				+ "        \"actors\": [\"actor 1\",\"actor 2\"]\n"
				+ "    }\n"
				+ "}";
		
		var movieDTO = mapper.readValue(jsonObject, MovieDTO.class);
		
		var movie = this.modelMapper.map(movieDTO, Movie.class);
	
		this.movieService.save(movie);		
		
		var movieLoaded = this.movieService.load(movie.getYear(), movie.getTitle());
		
		assertEquals(movieDTO.getTitle(), movieLoaded.getTitle());
		assertEquals(movieDTO.getYear(), movieLoaded.getYear());
	}

}
