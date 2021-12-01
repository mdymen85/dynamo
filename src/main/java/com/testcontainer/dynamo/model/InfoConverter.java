package com.testcontainer.dynamo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class InfoConverter implements DynamoDBTypeConverter<Object, Info> {

	@Override
	public Object convert(Info object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Info unconvert(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}
