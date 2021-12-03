package com.testcontainer.dynamo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InfoDTO {

	private List<String> directors;
	@JsonProperty(value = "release_date")
	private String releaseDate;
	private Double rating;	
	private List<String> genres;
	@JsonProperty(value = "image_url")
	private String imageUrl;
	private String plot;
	@JsonProperty(value = "running_time_secs")
	private Integer runningTimeSecs;
	private List<String> actors;
	
}
