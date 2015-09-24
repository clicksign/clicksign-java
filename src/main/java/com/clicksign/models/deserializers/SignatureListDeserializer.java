package com.clicksign.models.deserializers;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.clicksign.models.Signature;
import com.clicksign.models.SignatureList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class SignatureListDeserializer implements JsonDeserializer<SignatureList> {

	public SignatureList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject list = json.getAsJsonObject();

		System.out.println("ListDeserializer!" + context);
		for (Map.Entry<String, JsonElement> entry : list.entrySet()) {
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

		Date createdAt = context.deserialize(list.get("created_at"), Date.class);
		Date updatedAt = context.deserialize(list.get("updated_at"), Date.class);
		Date startedAt = context.deserialize(list.get("started_at"), Date.class);

		String userKey = list.get("user_key").getAsString();
		List<Signature> signatures = null;// list.get("signatures").;

		return new SignatureList(createdAt, startedAt, updatedAt, userKey, signatures);
	}
}
