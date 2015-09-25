package com.clicksign.models.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.clicksign.models.Signature;
import com.clicksign.models.SignatureList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class SignatureListDeserializer implements JsonDeserializer<SignatureList> {

	public SignatureList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject list = json.getAsJsonObject();

		Date createdAt = context.deserialize(list.get("created_at"), Date.class);
		Date updatedAt = context.deserialize(list.get("updated_at"), Date.class);
		Date startedAt = context.deserialize(list.get("started_at"), Date.class);
		String userKey = context.deserialize(list.get("user_key"), String.class);
		Type listType = new TypeToken<ArrayList<Signature>>() {
		}.getType();
		List<Signature> signatures = context.deserialize(list.get("signatures"), listType);

		return new SignatureList(createdAt, startedAt, updatedAt, userKey, signatures);
	}
}
