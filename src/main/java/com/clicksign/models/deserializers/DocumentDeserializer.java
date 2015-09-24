package com.clicksign.models.deserializers;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.clicksign.models.Document;
import com.clicksign.models.SignatureList;
import com.clicksign.net.ClicksignResource;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class DocumentDeserializer implements JsonDeserializer<Document> {

	public Document deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject document = json.getAsJsonObject().getAsJsonObject("document");

		System.out.println("DocumentDeserializer!");
		for (Map.Entry<String, JsonElement> entry : document.entrySet()) {
			String key = entry.getKey();
			System.out.print(key);
			JsonElement element = entry.getValue();
			if (element.isJsonPrimitive()) {
				System.out.print(" : " + element.getAsString());
			} else {
				System.out.print(" : - " + element.toString());
			}
			System.out.println();
		}

		String key = document.get("key").getAsString();
		String originalName = document.get("original_name").getAsString();
		String status = document.get("status").getAsString();

		Date createdAt = context.deserialize(document.get("created_at"), Date.class);
		Date updatedAt = context.deserialize(document.get("updated_at"), Date.class);

		String userKey = document.get("user_key").getAsString();
		SignatureList list = null; // context.deserialize(document.getAsJsonObject("list"),
									// SignatureList.class);

		return new Document(key, originalName, status, createdAt, updatedAt, userKey, list);
	}

}
