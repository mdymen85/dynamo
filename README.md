# DynamoDB

This project aims to help to know a little code about DynamoDB, to know how it works, and to introduce some concepts of **testcontainer** to create unit test with a docker image. To achive this I needed to use spring profile to add special configuration to test environment in **aplication.yml**:

```
spring:
  profile:
    active: test
```

In order to use **DynamoDB** in spring I added the following dependency in pom.xml:

```
<!-- https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-dynamodb -->
<dependency>
	<groupId>com.amazonaws</groupId>
	<artifactId>aws-java-sdk-dynamodb</artifactId>
	<version>1.12.120</version>
</dependency>
```

## Docker

I installed DynamoDB in a docker container to use it when I run de application -**not in unit testing**:

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
The application will validate if the table exists, if don't will create it; -this point can be improved-. The DynamoDB Hash Key its the year of the movie, and the DynamoDB Range Key is the title. Maybe this last approach can be improved.

Request to load a movie:

```
curl --location --request GET 'localhost:8080/api/v1/movie/2023/Movie title'
```

## Test Container

It's necessary to add this dependency of **testcontainer** to run a docker container of **DynamoDB**. When run the unit tests, the context will create the container with the coniguration setted in tested environment. 

```
<!-- https://mvnrepository.com/artifact/org.testcontainers/junit-jupiter -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>1.16.2</version>
    <scope>test</scope>
</dependency>

```
Also, i created a test that begins from the endpoint, using **Rest Assured**.
