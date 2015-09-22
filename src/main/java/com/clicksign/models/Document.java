package com.clicksign.models;

import java.util.Date;

import com.clicksign.net.ClicksignResource;

public class Document extends ClicksignResource {
	String key;
	String originalName;
	String status;
	Date created_at;
	Date updated_at;
	String userKey;
	SignatureList list;
}
