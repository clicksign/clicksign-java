package com.clicksign.models;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

	public Document() {
		super();
	}

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

	public static Document create(File file) throws ClicksignException {
		return create(file, null, null, null, null);
	}

	public static Document create(File file, String accessToken) throws ClicksignException {
		return create(file, null, null, null, accessToken);
	}

	public static Document create(File file, List<Signature> signers) throws ClicksignException {
		return create(file, signers, null, null, null);
	}

	public static Document create(File file, List<Signature> signers, String message, Boolean skipEmail,
			String accessToken) throws ClicksignException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("document[archive][original]", file);
		if (signers != null) {
			params.put("signers", new SignatureList(signers));
		}
		if (message != null) {
			params.put("message", message);
		}
		if (skipEmail != null) {
			params.put("skip_email", skipEmail);
		}

		return request(RequestMethod.POST, UrlBuilder.classURL(Document.class), params, Document.class, accessToken);
	}

	public static Document createList(String key, List<Signature> signers, Boolean skipEmail)
			throws ClicksignException {
		return createList(key, signers, skipEmail, null);
	}

	public static Document createList(String key, List<Signature> signers) throws ClicksignException {
		return createList(key, signers, Boolean.TRUE, null);
	}

	public static Document createList(String key, List<Signature> signers, String accessToken)
			throws ClicksignException {
		return createList(key, signers, Boolean.TRUE, accessToken);
	}

	public static Document createList(String key, List<Signature> signers, Boolean skipEmail, String accessToken)
			throws ClicksignException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip_email", skipEmail);
		params.put("signers", new SignatureList(signers));

		return request(RequestMethod.POST, UrlBuilder.instanceURL(Document.class, key, "list"), params, Document.class,
				accessToken);
	}

	public static Document resend(String key, String email, String message) throws ClicksignException {
		return resend(key, email, message, null);
	}

	public static Document resend(String key, String email, String message, String accessToken)
			throws ClicksignException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("message", message);

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

	public static InputStream download(String key) throws ClicksignException {
		return download(key, null);
	}

	public static InputStream download(String key, String accessToken) throws ClicksignException {
		return getDownloadInputStream(UrlBuilder.instanceURL(Document.class, key, "download"), accessToken);
	}
}
