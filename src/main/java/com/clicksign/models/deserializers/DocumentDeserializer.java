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

		String key = context.deserialize(document.get("key"), String.class);
		String originalName = context.deserialize(document.get("original_name"), String.class);
		String status = context.deserialize(document.get("status"), String.class);;

		Date createdAt = context.deserialize(document.get("created_at"), Date.class);
		Date updatedAt = context.deserialize(document.get("updated_at"), Date.class);

		String userKey = context.deserialize(document.get("user_key"), String.class);
		SignatureList list = context.deserialize(document.getAsJsonObject("list"),
							SignatureList.class);

		return new Document(key, originalName, status, createdAt, updatedAt, userKey, list);
	}

}
