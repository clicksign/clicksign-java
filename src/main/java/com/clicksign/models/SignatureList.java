package com.clicksign.models;

import java.util.Date;
import java.util.List;

public class SignatureList {
	Date createdAt;
	Date startedAt;
	Date updatedAt;
	String userKey;
	List<Signature> signatures;

	public SignatureList(Date createdAt, Date startedAt, Date updatedAt, String userKey, List<Signature> signatures) {
		super();
		this.createdAt = createdAt;
		this.startedAt = startedAt;
		this.updatedAt = updatedAt;
		this.userKey = userKey;
		this.signatures = signatures;
	}

	public SignatureList(List<Signature> signatures) {
		super();
		this.signatures = signatures;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
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

	public List<Signature> getSignatures() {
		return signatures;
	}

	public void setSignatures(List<Signature> signatures) {
		this.signatures = signatures;
	}
}
