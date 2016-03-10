package com.clicksign.models;

import java.util.Date;

public class Signature {
	String displayName;
	String title;
	String companyName;
	String documentation;
	String key;
	String act;
	String allow_method;
	String phone_number;
	String decision;
	String address;
	String email;
	Date signedAt;
	Date createdAt;
	Date updatedAt;

	public Signature(String email, String act, String allow_method, String phone_number) {
		super();
		this.act = act;
		this.email = email;
		this.allow_method = allow_method;
		this.phone_number = phone_number;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}
	
	public String getAllowMethod() {
		return allow_method;
	}

	public void setAllowMethod(String allow_method) {
		this.allow_method = allow_method;
	}
	
	public String getPhoneNumber() {
		return phone_number;
	}

	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getSignedAt() {
		return signedAt;
	}

	public void setSignedAt(Date signedAt) {
		this.signedAt = signedAt;
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
}
