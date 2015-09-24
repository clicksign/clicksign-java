package com.clicksign.models;

import java.util.Map;

import com.clicksign.exception.ClicksignException;
import com.clicksign.net.ClicksignResource;
import com.clicksign.net.UrlBuilder;

public class Hook extends ClicksignResource {
	Document document;
	String url;

	public Hook(Document document, String url) {
		super();
		this.document = document;
		this.url = url;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		// TODO use Url as a parameter
		Map<String, Object> params = null;
		return request(RequestMethod.POST, UrlBuilder.instanceURL(Document.class, key, Hook.class), params, Hook.class,
				accessToken);
	}

	public static Hook delete(String key, String url) throws ClicksignException {
		return delete(key, url, null);
	}

	public static Hook delete(String key, String url, String accessToken) throws ClicksignException {
		return request(RequestMethod.DELETE, UrlBuilder.instanceURL(Document.class, key, Hook.class), null, Hook.class,
				accessToken);
	}
}