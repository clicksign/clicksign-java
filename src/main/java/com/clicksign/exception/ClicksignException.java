package com.clicksign.exception;

public class ClicksignException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String param;

	public ClicksignException(String message) {
		super(message, null);
		this.param = null;
	}

	public ClicksignException(String message, Throwable e) {
		super(message, e);
		this.param = null;
	}

	public ClicksignException(String message, String param, Throwable e) {
		super(message, e);
		this.param = param;
	}

	public String getParam() {
		return param;
	}
}
