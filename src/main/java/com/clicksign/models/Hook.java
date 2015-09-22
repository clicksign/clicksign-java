package com.clicksign.models;

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
}
