package com.clicksign.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.clicksign.Clicksign;
import com.clicksign.exception.ClicksignException;
import com.clicksign.models.Batch;
import com.clicksign.models.Document;
import com.clicksign.models.Hook;
import com.clicksign.models.deserializers.BatchDeserializer;
import com.clicksign.models.deserializers.DocumentDeserializer;
import com.clicksign.models.deserializers.HookDeserializer;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClicksignResource {
	public static final String CHARSET = "UTF-8";
	
	public static final Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.registerTypeAdapter(Batch.class, new BatchDeserializer())
			.registerTypeAdapter(Document.class, new DocumentDeserializer())
			.registerTypeAdapter(Hook.class, new HookDeserializer())
			.create();
	
	protected enum RequestMethod {
		GET, POST, DELETE, PUT
	}
	
	protected static <T> T _request(ClicksignResource.RequestMethod method, String url, Map<String, Object> params, Class<T> clazz, String accessToken) throws ClicksignException {
		if ((Clicksign.accessToken == null || Clicksign.accessToken.length() == 0) && (accessToken == null || accessToken.length() == 0)) {
			throw new ClicksignException(
				"Access Token não fornecido. Configure seu Access Token com 'Clicksign.accessToken = {TOKEN}'. "
					+ "Envie email para suporte@clicksign.com para obter ajuda.");
		}

		if (accessToken == null) {
			accessToken = Clicksign.accessToken;
		}

		String query;
		try {
			query = createQuery(params);
		} catch (UnsupportedEncodingException e) {
			throw new ClicksignException("Unable to encode parameters to "
				+ CHARSET
				+ ". Please email contact@easypost.com for assistance.", e);
		}

        // System.out.println(url);

		ClicksignResponse response = makeURLConnectionRequest(method, url, query, accessToken);

		int rCode = response.responseCode;
		String rBody = response.responseBody;
		if (rCode < 200 || rCode >= 300) {
			handleAPIError(rBody, rCode);
		}

		return gson.fromJson(rBody, clazz);
	}
	
	private static final String CUSTOM_URL_STREAM_HANDLER_PROPERTY_NAME = "com.clicksign.net.customURLStreamHandler";

	private static ClicksignResponse makeURLConnectionRequest(RequestMethod method, String url, String query,
			String accessToken) throws ClicksignException {
		javax.net.ssl.HttpsURLConnection conn = null;
		try {
			switch (method) {
			case GET:
				conn = createGetConnection(url, query, accessToken);
				break;
			case POST:
				conn = createPostConnection(url, query, accessToken);
				break;
			case DELETE:
				conn = createDeleteConnection(url, query, accessToken);
				break;
			case PUT:
				conn = createPutConnection(url, query, accessToken);
				break;
			default:
				throw new ClicksignException(
					String.format("Método HTTP desconhecido: %s. Por favor entre em contato via suport@clicksign.com.", method));
			}
			int rCode = conn.getResponseCode(); // sends the request
			String rBody = null;
			if (rCode >= 200 && rCode < 300) {
				rBody = getResponseBody(conn.getInputStream());
			} else {
				rBody = getResponseBody(conn.getErrorStream());
			}
			return new ClicksignResponse(rCode, rBody);
		} catch (IOException e) {
			throw new ClicksignException(
				String.format(
					"Não foi possível conectar-se à Clicksign (%s). "
						+ "Por favor verifique sua conexão de internet e tente novamente. Se este problema persistir,"
						+ "por favor nos contate via suporte@clicksign.com.", Clicksign.API_BASE), e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	private static String getResponseBody(InputStream responseStream) throws IOException {
		Scanner scanner = new Scanner(responseStream, CHARSET);
		String rBody = scanner.useDelimiter("\\A").next();
		responseStream.close();
		scanner.close();
		return rBody;
	}

	private static HttpsURLConnection createGetConnection(String url, String query, String accessToken) throws IOException {
		String getURL = String.format("%s?%s", url, query);
		javax.net.ssl.HttpsURLConnection conn = createClicksignConnection(getURL, accessToken);
		conn.setRequestMethod("GET");
		return conn;
	}

	private static HttpsURLConnection createPostConnection(String url, String query, String accessToken) throws IOException {
		javax.net.ssl.HttpsURLConnection conn = createClicksignConnection(url, accessToken);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", String.format("application/x-www-form-urlencoded;charset=%s", CHARSET));
		OutputStream output = null;
		try {
			output = conn.getOutputStream();
			output.write(query.getBytes(CHARSET));
		} finally {
			if (output != null) {
				output.close();
			}
		}
		return conn;
	}
	
	private static HttpsURLConnection createPutConnection(String url, String query, String accessToken) throws IOException {
		String putUrl = String.format("%s?%s", url, query);
		javax.net.ssl.HttpsURLConnection conn = createClicksignConnection(putUrl, accessToken);
		conn.setRequestMethod("PUT");
		return conn;
	}

	private static HttpsURLConnection createDeleteConnection(String url, String query, String accessToken) throws IOException {
		String deleteUrl = String.format("%s?%s", url, query);
		javax.net.ssl.HttpsURLConnection conn = createClicksignConnection(deleteUrl, accessToken);
		conn.setRequestMethod("DELETE");
		return conn;
	}


	private static HttpsURLConnection createClicksignConnection(String url, String accessToken) throws IOException {
		System.err.println("createClicksignConnection(String url, String accessToken): " + url) ;
		URL clicksignURL = null;
		String customURLStreamHandlerClassName = System.getProperty(CUSTOM_URL_STREAM_HANDLER_PROPERTY_NAME, null);
		if (customURLStreamHandlerClassName != null) {
			// instantiate the custom handler provided
			try {
				@SuppressWarnings("unchecked")
				Class<URLStreamHandler> clazz = (Class<URLStreamHandler>) Class.forName(customURLStreamHandlerClassName);
				Constructor<URLStreamHandler> constructor = clazz.getConstructor();
				URLStreamHandler customHandler = constructor.newInstance();
				clicksignURL = new URL(null, url, customHandler);
			} catch (ClassNotFoundException e) {
				throw new IOException(e);
			} catch (SecurityException e) {
				throw new IOException(e);
			} catch (NoSuchMethodException e) {
				throw new IOException(e);
			} catch (IllegalArgumentException e) {
				throw new IOException(e);
			} catch (InstantiationException e) {
				throw new IOException(e);
			} catch (IllegalAccessException e) {
				throw new IOException(e);
			} catch (InvocationTargetException e) {
				throw new IOException(e);
			}
		} else {
			clicksignURL = new URL(url);
		}
		javax.net.ssl.HttpsURLConnection conn = (javax.net.ssl.HttpsURLConnection) clicksignURL.openConnection();
		conn.setConnectTimeout(20000); // 20 seconds
		conn.setReadTimeout(40000); // 40 seconds
		conn.setUseCaches(false);

		return conn;
	}
	
	private static String createQuery(Map<String, Object> params) throws UnsupportedEncodingException {
		Map<String, String> flatParams = flattenParams(params);
		StringBuffer queryStringBuffer = new StringBuffer();
		for (Map.Entry<String, String> entry : flatParams.entrySet()) {
			queryStringBuffer.append("&");
			queryStringBuffer.append(urlEncodePair(entry.getKey(), entry.getValue()));
		}
		if (queryStringBuffer.length() > 0) {
			queryStringBuffer.deleteCharAt(0);
		}
		return queryStringBuffer.toString();
	}

	private static Map<String, String> flattenParams(Map<String, Object> params) {
		if (params == null) {
			return new HashMap<String, String>();
		}
		Map<String, String> flatParams = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Map<?, ?>) {
				Map<String, Object> flatNestedMap = new HashMap<String, Object>();
				Map<?, ?> nestedMap = (Map<?, ?>) value;
				for (Map.Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
					flatNestedMap.put(
						String.format("%s[%s]", key, nestedEntry.getKey()),
						nestedEntry.getValue());
				}
				flatParams.putAll(flattenParams(flatNestedMap));
			} else if (value instanceof List) {
				Map<String, Object> flatNestedMap = new HashMap<String, Object>();
				List<?> nestedList = (List<?>) value;
				for (int i = 0; i < nestedList.size(); i++) {
					flatNestedMap.put(
						String.format("%s[%s]", key, i),
						nestedList.get(i));
					flatParams.putAll(flattenParams(flatNestedMap));
				}
			} else if (value instanceof ClicksignResource) {
				flatParams.put(
						String.format("%s[%s]", key, "id"),
						value.toString());

			} else if (value != null) {
				flatParams.put(key, value.toString());
			}
		}
        System.out.println("ClicksignResource.flattenParams:"+ flatParams);

		return flatParams;
	}
	
	private static String urlEncodePair(String k, String v) throws UnsupportedEncodingException {
		return String.format("%s=%s", URLEncoder.encode(k, CHARSET), URLEncoder.encode(v, CHARSET));
	}
	
	private static class Error {
		@SuppressWarnings("unused")
		String type;
		String message;
		String code;
		String param;
		String error;
	}
	
	private static void handleAPIError(String rBody, int rCode) throws ClicksignException {
		try {
			ClicksignResource.Error error = gson.fromJson(rBody, ClicksignResource.Error.class);

			if(error.error.length() > 0) {
				throw new ClicksignException(error.error);
			}

			throw new ClicksignException(error.message, error.param, null);
		} catch (Exception e) {
            throw new ClicksignException(String.format("An error occured. Response code: %s Response body: %s", rCode, rBody));
        }
	}
}

