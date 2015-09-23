package com.clicksign.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.clicksign.exception.ClicksignException;
import com.clicksign.net.ClicksignResource;

public class Batch extends ClicksignResource {
	List<String> keys;
	String key;
	Date createdAt;
	Date updatedAt;

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public static BatchCollection all(String accessToken) throws ClicksignException {
		return request(RequestMethod.GET, classURL(Batch.class), null, BatchCollection.class, accessToken);
	}

	public static Batch create(List<String> keys, String accessToken) throws ClicksignException {
		// TODO set keys in the params object
		Map<String, Object> params = null;

		return request(RequestMethod.POST, classURL(Batch.class), params, Batch.class, accessToken);
	}

	public static Batch delete(String id, String accessToken) throws ClicksignException {
		return request(RequestMethod.DELETE, instanceURL(Batch.class, id), null, Batch.class, accessToken);
	}
}
