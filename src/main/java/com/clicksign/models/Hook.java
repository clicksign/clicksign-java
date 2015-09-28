package com.clicksign.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.clicksign.exception.ClicksignException;
import com.clicksign.net.ClicksignResource;
import com.clicksign.net.UrlBuilder;

public class Hook extends ClicksignResource {
	String id;
	String url;
	Date createdAt;
	Date updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public static HookCollection all(String key) throws ClicksignException {
		return all(key, null);
	}

	public static HookCollection all(String key, String accessToken) throws ClicksignException {
		return request(RequestMethod.GET, UrlBuilder.instanceURL(Document.class, key, Hook.class), null,
				HookCollection.class, accessToken);
	}

	public static Hook create(String key, String url) throws ClicksignException {
		return create(key, url, null);
	}

	public static Hook create(String key, String url, String accessToken) throws ClicksignException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("url", url);

		return request(RequestMethod.POST, UrlBuilder.instanceURL(Document.class, key, Hook.class), params, Hook.class,
				accessToken);
	}

	public static void delete(String key, String id) throws ClicksignException {
		delete(key, id, null);
	}

	public static void delete(String key, String id, String accessToken) throws ClicksignException {
		request(RequestMethod.DELETE, UrlBuilder.instanceURL(Document.class, key, Hook.class, id), null, Hook.class,
				accessToken);
	}
}