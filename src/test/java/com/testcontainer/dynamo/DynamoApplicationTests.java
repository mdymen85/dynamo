package com.testcontainer.dynamo;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
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

@SpringBootTest
@Slf4j
@Testcontainers
//@RequiredArgsConstructor
@ContextConfiguration(classes = DynamoTestConfiguration.class)
class DynamoApplicationTests extends AbstractDynamoInit {
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private DynamoDBMapper dynamoMapper;
	
	@Container
	public static GenericContainer dynamoDBLocal =
	    new GenericContainer("amazon/dynamodb-local:1.11.477")
	        .withExposedPorts(PORT);
	
	public class DynamoTestConfiguration {

//		@Bean(name = "amazonDynamoDB")
//		public AmazonDynamoDB createConnection() {
//			return AmazonDynamoDBClientBuilder.standard()
//			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
//			.build();		
//		}
	//	
//		@Bean
//		public DynamoDB createDynamoDB(AmazonDynamoDB client) {
//			return new DynamoDB(client);
//		}
	//	
//		@Bean 
//		public DynamoDBMapper createMapper(AmazonDynamoDB client) {
//			return new DynamoDBMapper(client);
//		}
	//	
		@Bean
		public ObjectMapper objectMapper() {
			return new ObjectMapper();
		}
		
	}
	
//	@BeforeAll
//	public static void init() {
//		createTable();
//	}
	
	@Test
	void createMovie() throws JsonMappingException, JsonProcessingException {
	//	this.createTable();
		
		var dmapper = this.contextLoads2();
		
		var movieDTO = mapper.readValue("{\n"
				+ "    \"year\":\"2023\",\n"
				+ "    \"title\":\"Movie title\",\n"
				+ "    \"info\": {\n"
				+ "        \"directors\": [\"Michael Perez\", \"Carl Harris\"],\n"
				+ "        \"release_date\": \"2013-09-02T00:00:00Z\",\n"
				+ "        \"actors\": [\"actor 1\",\"actor 2\"]\n"
				+ "    }\n"
				+ "}", MovieDTO.class);
		
		var movie = this.modelMapper.map(movieDTO, Movie.class);
	
		this.movieService.save(movie);
		
		dmapper.save(movie);
		
		assertTrue(true);
		
		
	}
//	
//	
	DynamoDBMapper contextLoads2() {

        String serviceEndpoint = buildEndpointUrl();
        AmazonDynamoDB client = createAmazonDynamoDBClient(serviceEndpoint);
        createConnection(client);
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        
        this.createTable(new DynamoDB(client));
        
        return mapper;
        
	}
	
	@Test
	void contextLoads() {
        String tableHashKey = "id";
        String tableName = "test-table";

        String serviceEndpoint = buildEndpointUrl();
        AmazonDynamoDB client = createAmazonDynamoDBClient(serviceEndpoint);
        CreateTableResult table = createCreateDynamoDBTable(client, tableName, tableHashKey);

        log.info("Created table: {}", table.getTableDescription());
	}
//	
    @NotNull
    private String buildEndpointUrl() {
        String serviceEndpoint = "http://localhost:";

        if (dynamoDBLocal.isRunning()) {
            serviceEndpoint += dynamoDBLocal.getFirstMappedPort();
        } else {
        	log.info("logging.... {}", dynamoDBLocal.getLogs());
            throw new IllegalStateException("DynamoDB Local is not running.");
        }

//        logger.info("Using DynamoDB endpoint: {}", serviceEndpoint);

        return serviceEndpoint;
    }
    
    private AmazonDynamoDB createAmazonDynamoDBClient(String serviceEndpoint) {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, "us-east-1");

        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }
//    
    private CreateTableResult createCreateDynamoDBTable(AmazonDynamoDB client, String tableName, String tableHashKey) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition(tableHashKey, ScalarAttributeType.S));

        List<KeySchemaElement> ks = new ArrayList<>();
        ks.add(new KeySchemaElement(tableHashKey, KeyType.HASH));

        ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput(10L, 10L);

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(ks)
                .withProvisionedThroughput(provisionedthroughput);

        return client.createTable(request);
    }
//    
    public void createConnection(AmazonDynamoDB client ) {
//    	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//		  .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:"+PORT, "us-west-2"))
//		  .build();

		DynamoDB dynamoDB = new DynamoDB(client);

      String tableName = "Movies";

      try {
          System.out.println("Attempting to create table; please wait...");
          Table table = dynamoDB.createTable(tableName,
              Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
                                                                        // key
                  new KeySchemaElement("title", KeyType.RANGE)), // Sort key
              Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                  new AttributeDefinition("title", ScalarAttributeType.S)),
              new ProvisionedThroughput(10L, 10L));
          table.waitForActive();
          System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

      }
      catch (Exception e) {
          System.err.println("Unable to create table: ");
          System.err.println(e.getMessage());
      }
    }



}
