package com.clicksign.models;

import java.util.Date;
import java.util.Map;

import com.clicksign.exception.ClicksignException;
import com.clicksign.net.ClicksignResource;
import com.clicksign.net.UrlBuilder;

public class Document extends ClicksignResource {
	String key;
	String originalName;
	String status;
	Date createdAt;
	Date updatedAt;
	String userKey;
	SignatureList list;

	public Document(String key, String originalName, String status, Date createdAt, Date updatedAt, String userKey,
			SignatureList list) {
		super();
		this.key = key;
		this.originalName = originalName;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userKey = userKey;
		this.list = list;
	}

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

	public static DocumentCollection all() throws ClicksignException {
		return all(null);
	}

	public static DocumentCollection all(String accessToken) throws ClicksignException {
		return request(RequestMethod.GET, UrlBuilder.classURL(Document.class), null, DocumentCollection.class,
				accessToken);
	}

	public static Document find(String key) throws ClicksignException {
		return find(key, null);
	}

	public static Document find(String key, String accessToken) throws ClicksignException {
		return request(RequestMethod.GET, UrlBuilder.instanceURL(Document.class, key), null, Document.class,
				accessToken);
	}

	public static Document create(String key) throws ClicksignException {
		return create(key, null);
	}

	public static Document create(String key, String accessToken) throws ClicksignException {
		// TODO get a FileStream, etc.
		return request(RequestMethod.POST, UrlBuilder.instanceURL(Document.class, key), null, Document.class,
				accessToken);
	}

	public static Document createList(String key, SignatureList list, Boolean skipEmail) throws ClicksignException {
		return createList(key, list, skipEmail, null);
	}

	public static Document createList(String key, SignatureList list, Boolean skipEmail, String accessToken)
			throws ClicksignException {
		// TODO transform list in parameters, and use skipEmail
		Map<String, Object> params = null;

		return request(RequestMethod.POST, UrlBuilder.instanceURL(Document.class, key, "list"), params, Document.class,
				accessToken);
	}

	public static Document resend(String key, String email, String message) throws ClicksignException {
		return resend(key, email, message, null);
	}

	public static Document resend(String key, String email, String message, String accessToken)
			throws ClicksignException {
		// TODO use email and message in the params
		Map<String, Object> params = null;

		return request(RequestMethod.POST, UrlBuilder.instanceURL(Document.class, key, "resend"), params,
				Document.class, accessToken);
	}

	public static Document cancel(String key) throws ClicksignException {
		return cancel(key, null);
	}

	public static Document cancel(String key, String accessToken) throws ClicksignException {
		return request(RequestMethod.POST, UrlBuilder.instanceURL(Document.class, key, "cancel"), null, Document.class,
				accessToken);
	}

	public static Document download(String key) throws ClicksignException {
		return download(key, null);
	}

	public static Document download(String key, String accessToken) throws ClicksignException {
		// TODO get a FileStream, etc
		return request(RequestMethod.GET, UrlBuilder.instanceURL(Document.class, key, "download"), null, Document.class,
				accessToken);
	}
}
