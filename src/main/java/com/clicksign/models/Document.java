package com.clicksign.models;

import java.util.Date;
import java.util.Map;

import com.clicksign.exception.ClicksignException;
import com.clicksign.net.ClicksignResource;

public class Document extends ClicksignResource {
	String key;
	String originalName;
	String status;
	Date created_at;
	Date updated_at;
	String userKey;
	SignatureList list;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public SignatureList getList() {
		return list;
	}

	public void setList(SignatureList list) {
		this.list = list;
	}

	public static Document find(String key) throws ClicksignException {
		return find(key, null);
	}

	public static DocumentCollection all(String accessToken) throws ClicksignException {
		return request(RequestMethod.GET, classURL(Document.class), null, DocumentCollection.class, accessToken);
	}

	public static Document find(String key, String accessToken) throws ClicksignException {
		return request(RequestMethod.GET, instanceURL(Document.class, key), null, Document.class, accessToken);
	}

	public static Document create(String key, String accessToken) throws ClicksignException {
		// TODO get a FileStream, etc.
		return request(RequestMethod.POST, instanceURL(Document.class, key), null, Document.class, accessToken);
	}

	public static Document createList(String key, SignatureList list, Boolean skipEmail, String accessToken)
			throws ClicksignException {
		// TODO transform list in parameters, and use skipEmail
		Map<String, Object> params = null;

		return request(RequestMethod.POST, instanceURL(Document.class, key, "list"), params, Document.class,
				accessToken);
	}

	public static Document resend(String key, String email, String message, String accessToken)
			throws ClicksignException {
		// TODO use email and message in the params
		Map<String, Object> params = null;

		return request(RequestMethod.POST, instanceURL(Document.class, key, "resend"), params, Document.class,
				accessToken);
	}

	public static Document cancel(String key, String accessToken) throws ClicksignException {
		return request(RequestMethod.POST, instanceURL(Document.class, key, "cancel"), null, Document.class,
				accessToken);
	}

	public static Document download(String key, String accessToken) throws ClicksignException {
		// TODO get a FileStream, etc
		return request(RequestMethod.GET, instanceURL(Document.class, key, "download"), null, Document.class,
				accessToken);
	}
}
