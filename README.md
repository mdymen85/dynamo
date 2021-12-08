# DynamoDB

This project aims to help someone to know a little code about DynamoDB, to know how it works, and to introduce some concepts of **testcontainer** to create unit test with a docker image. To achive this i needed to use spring profile.

I added the following dependency in pom.xml:

```
		<!-- https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-dynamodb -->
		<dependency>
		    <groupId>com.amazonaws</groupId>
		    <artifactId>aws-java-sdk-dynamodb</artifactId>
		    <version>1.12.120</version>
		</dependency>
```

## Docker

I installed DynamoDB in a docker container in order to use it:

```
docker run -p 8000:8000 amazon/dynamodb-local
```

## How it works

I created two rest endpoints: to save a movie and to load it. Open the project and run the application on port 8080.
Request to save a movie:

```
curl --location --request POST 'localhost:8080/api/v1/movie' \
--header 'Content-Type: application/json' \
--data-raw '{
    "year":"2023",
    "title":"Movie title",
    "info": {
        "directors": ["Michael Perez", "Carl Harris"],
        "release_date": "2013-09-02T00:00:00Z",
        "raiting": "2.2",
        "actors": ["actor 1","actor 2"]
    }
}'
```

Request to load a movie:

```
curl --location --request GET 'localhost:8080/api/v1/movie/2023/Movie title'
```

## Test Container

```
    <!-- https://mvnrepository.com/artifact/org.testcontainers/testcontainers -->
		<dependency>
		    <groupId>org.testcontainers</groupId>
		    <artifactId>testcontainers</artifactId>
		    <version>1.16.2</version>
		    <scope>test</scope>
		</dependency>

		<!-- https://mvnrepository.com/artifact/org.testcontainers/junit-jupiter -->
		<dependency>
		    <groupId>org.testcontainers</groupId>
		    <artifactId>junit-jupiter</artifactId>
		    <version>1.16.2</version>
		    <scope>test</scope>
		</dependency>

```
