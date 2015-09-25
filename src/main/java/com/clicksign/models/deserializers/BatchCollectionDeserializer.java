package com.clicksign.models.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.clicksign.models.Batch;
import com.clicksign.models.BatchCollection;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class BatchCollectionDeserializer implements JsonDeserializer<BatchCollection> {

	public BatchCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonArray list = json.getAsJsonArray();
		Type batchType = new TypeToken<ArrayList<Batch>>() {
		}.getType();

		List<Batch> batches = context.deserialize(list, batchType);
		return new BatchCollection(batches);
	}
}
