package com.clicksign.models;

import java.util.List;

import com.clicksign.net.ClicksignResource;

public class DocumentCollection extends ClicksignResource {
	List<Document> documents;

	public DocumentCollection(List<Document> documents) {
		super();
		this.documents = documents;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}
