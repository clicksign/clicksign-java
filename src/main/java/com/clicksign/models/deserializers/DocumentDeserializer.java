package com.clicksign.models.deserializers;

import java.lang.reflect.Type;

import com.clicksign.models.Document;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DocumentDeserializer implements JsonDeserializer<Document> {

	public Document deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
