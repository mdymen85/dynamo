package com.testcontainer.dynamo;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcontainer.dynamo.dto.MovieDTO;
import com.testcontainer.dynamo.model.Movie;
import com.testcontainer.dynamo.service.MovieService;

/**
 * Need to run with profile: test
 * -Dspring.active.profile=test
 * 
 * @author Martin Dymenstein
 *
 */
@SpringBootTest
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
