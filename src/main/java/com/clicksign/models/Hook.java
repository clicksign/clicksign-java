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
}
