package com.clicksign.models;

import java.util.Map;

import com.clicksign.exception.ClicksignException;
import com.clicksign.net.ClicksignResource;

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

	public static HookCollection all(String key, String accessToken) throws ClicksignException {
		return request(RequestMethod.GET, instanceURL(Document.class, key, Hook.class), null, HookCollection.class,
				accessToken);
	}

	public static Hook create(String key, String url, String accessToken) throws ClicksignException {
		// TODO use Url as a parameter
		Map<String, Object> params = null;
		return request(RequestMethod.POST, instanceURL(Document.class, key, Hook.class), params, Hook.class,
				accessToken);
	}

	public static Hook delete(String key, String url, String accessToken) throws ClicksignException {
		return request(RequestMethod.DELETE, instanceURL(Document.class, key, Hook.class), null, Hook.class,
				accessToken);
	}
}
