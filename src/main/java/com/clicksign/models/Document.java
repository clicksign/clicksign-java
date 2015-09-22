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
}
