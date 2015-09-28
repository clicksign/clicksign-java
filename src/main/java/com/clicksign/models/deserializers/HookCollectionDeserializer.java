package com.clicksign.models.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.clicksign.models.Hook;
import com.clicksign.models.HookCollection;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class HookCollectionDeserializer implements JsonDeserializer<HookCollection> {
	public HookCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonArray list = json.getAsJsonArray();
		Type hookType = new TypeToken<ArrayList<Hook>>() {
		}.getType();

		List<Hook> hooks = context.deserialize(list, hookType);
		return new HookCollection(hooks);
	}

}
