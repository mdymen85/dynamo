package com.testcontainer.dynamo.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MovieDTO {

	private Integer year;
	
	private String title;  
	
	private InfoDTO info;
	
}
