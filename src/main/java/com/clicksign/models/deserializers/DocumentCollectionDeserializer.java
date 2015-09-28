package com.clicksign.models.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.clicksign.models.Document;
import com.clicksign.models.DocumentCollection;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class DocumentCollectionDeserializer implements JsonDeserializer<DocumentCollection> {

	public DocumentCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonArray list = json.getAsJsonArray();
		Type documentType = new TypeToken<ArrayList<Document>>() {
		}.getType();

		List<Document> documents = context.deserialize(list, documentType);
		return new DocumentCollection(documents);
	}

}
