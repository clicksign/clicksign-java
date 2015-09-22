package com.clicksign.models;

import java.util.Date;
import java.util.List;

import com.clicksign.net.ClicksignResource;

public class Batch extends ClicksignResource{
	List<String> keys;
	String key;
    Date createdAt;
    Date updatedAt;
}
