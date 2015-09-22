package com.clicksign.models;

import java.util.Date;
import java.util.List;

public class SignatureList {
	Date created_at;
	Date started_at;
	Date updated_at;
	String userKey;
	List<Signature> signatures;
}
