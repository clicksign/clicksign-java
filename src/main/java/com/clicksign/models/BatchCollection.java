package com.clicksign.models;

import java.util.List;

import com.clicksign.net.ClicksignResource;

public class BatchCollection extends ClicksignResource {
	List<Batch> batches;

	public BatchCollection(List<Batch> batches) {
		super();
		this.batches = batches;
	}

	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}
}
