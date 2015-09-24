package com.clicksign.net;

import com.clicksign.Clicksign;

public class UrlBuilder {

	public static String instanceURL(Class<?> clazz, String key, Class<?> klazz, String id) {
		return String.format("%s/%s/%s/%s", classURL(clazz), key, classURL(klazz), id);
	}

	public static String instanceURL(Class<?> clazz, String key) {
		return String.format("%s/%s", classURL(clazz), key);
	}

	public static String instanceURL(Class<?> clazz, String key, Class<?> klazz) {
		return String.format("%s/%s/%s", classURL(clazz), key, classURL(klazz));
	}

	public static String instanceURL(Class<?> clazz, String key, String action) {
		return String.format("%s/%s/%s", classURL(clazz), key, action);
	}

	public static String singleClassURL(Class<?> clazz) {
		return String.format("%s/%s/%s", Clicksign.API_BASE, Clicksign.apiVersion, className(clazz));
	}

	public static String classURL(Class<?> clazz) {
		String singleURL = singleClassURL(clazz);
		if (singleURL.charAt(singleURL.length() - 1) == 'h') {
			return String.format("%ses", singleClassURL(clazz));
		} else {
			return String.format("%ss", singleClassURL(clazz));
		}
	}

	private static String className(Class<?> clazz) {
		return clazz.getSimpleName().replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase().replace("$", "");
	}
}
