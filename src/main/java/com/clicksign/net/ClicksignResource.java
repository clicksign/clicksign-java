package com.clicksign.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.clicksign.Clicksign;
import com.clicksign.exception.ClicksignException;
import com.clicksign.models.BatchCollection;
import com.clicksign.models.Document;
import com.clicksign.models.DocumentCollection;
import com.clicksign.models.HookCollection;
import com.clicksign.models.Signature;
import com.clicksign.models.SignatureList;
import com.clicksign.models.deserializers.BatchCollectionDeserializer;
import com.clicksign.models.deserializers.DocumentCollectionDeserializer;
import com.clicksign.models.deserializers.DocumentDeserializer;
import com.clicksign.models.deserializers.HookCollectionDeserializer;
import com.clicksign.models.deserializers.SignatureListDeserializer;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClicksignResource implements Serializable {
	public static final String CHARSET = "UTF-8";

	private static final String USER_AGENT = String.format("Clicksign/Java %s", Clicksign.VERSION);

	public static final Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
			.registerTypeAdapter(BatchCollection.class, new BatchCollectionDeserializer())
			.registerTypeAdapter(Document.class, new DocumentDeserializer())
			.registerTypeAdapter(DocumentCollection.class, new DocumentCollectionDeserializer())
			.registerTypeAdapter(SignatureList.class, new SignatureListDeserializer())
			.registerTypeAdapter(HookCollection.class, new HookCollectionDeserializer()).create();

	private static final ContentType TEXT_PLAIN = ContentType.TEXT_PLAIN;

	protected enum RequestMethod {
		GET, POST, DELETE, PUT
	}

	public ClicksignResource(){
		super();
	}

	protected static InputStream getDownloadInputStream(String url, String accessToken) throws ClicksignException {
		accessToken = getAccessToken(accessToken);
		String finalUrl = formatFinalUrl(url, accessToken);
		HttpEntity emptyParams = buildRequestEntity(null);

		ClicksignResponse response = executeRequest(RequestMethod.GET, finalUrl, emptyParams);
		InputStream inputStream = null;
		if (response.getCode() == 200) {
			inputStream = response.getContent();
		} else if (response.getCode() == 202) {
			inputStream = null;
		}
		return inputStream;
	}

	protected static <T> T request(ClicksignResource.RequestMethod method, String url, Map<String, Object> params,
			Class<T> clazz, String accessToken) throws ClicksignException {

		accessToken = getAccessToken(accessToken);

		return _request(method, url, params, clazz, accessToken);
	}

	private static String getAccessToken(String accessToken) throws ClicksignException {
		if ((Clicksign.accessToken == null || Clicksign.accessToken.length() == 0)
				&& (accessToken == null || accessToken.length() == 0)) {
			throw new ClicksignException(
					"Access Token não fornecido. Configure seu Access Token com 'Clicksign.accessToken = {TOKEN}'. "
							+ "Envie email para suporte@clicksign.com para obter ajuda.");
		}

		if (accessToken == null) {
			accessToken = Clicksign.accessToken;
		}

		return accessToken;
	}

	protected static <T> T _request(ClicksignResource.RequestMethod method, String url, Map<String, Object> params,
			Class<T> clazz, String accessToken) throws ClicksignException {
		String finalUrl = formatFinalUrl(url, accessToken);
		HttpEntity reqEntity = buildRequestEntity(params);

		ClicksignResponse response = executeRequest(method, finalUrl, reqEntity);

		return gson.fromJson(response.getBody(), clazz);
	}

	private static ClicksignResponse executeRequest(RequestMethod method, String url, HttpEntity reqEntity)
			throws ClicksignException {

		HttpResponse response = null;

		try {
			switch (method) {
			case GET:
				response = executeGetRequest(url, reqEntity);
				break;
			case POST:
				response = executePostRequest(url, reqEntity);
				break;
			case PUT:
				response = executePutRequest(url, reqEntity);
				break;
			case DELETE:
				response = executeDeleteRequest(url, reqEntity);
				break;
			default:
				throw new ClicksignException(String.format(
						"Método HTTP desconhecido: %s. Por favor entre em contato via suporte@clicksign.com.", method));
			}
		} catch (IOException e) {
			throw new ClicksignException(String.format("Não foi possível conectar-se à Clicksign (%s). "
					+ "Por favor verifique sua conexão de internet e tente novamente. Se este problema persistir,"
					+ "por favor nos contate via suporte@clicksign.com.", Clicksign.endPoint), e);
		}

		ClicksignResponse clicksignResponse = new ClicksignResponse(response);
		checkErrors(clicksignResponse);

		return clicksignResponse;
	}

	private static void checkErrors(ClicksignResponse response) throws ClicksignException {
		int code = response.code;
		String body = response.body;

		if (code < 200 || code >= 300) {
			handleAPIError(body, code);
		}
	}

	private static void addHeaders(HttpEntityEnclosingRequestBase request) {
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("accept", "application/json");
	}

	private static void addHeaders(HttpRequestBase request) {
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("accept", "application/json");
	}

	private static HttpResponse executePostRequest(String url, HttpEntity reqEntity) throws IOException {
		HttpPost request = new HttpPost(url);
		addHeaders(request);
		request.setEntity(reqEntity);

		CloseableHttpClient client = createClicksignClient();

		return executeRequest(request, client);
	}

	private static HttpResponse executeGetRequest(String url, HttpEntity reqEntity) throws IOException {
		HttpGet request = new HttpGet(url);
		addHeaders(request);

		CloseableHttpClient client = createClicksignClient();

		return executeRequest(request, client);
	}

	private static HttpResponse executePutRequest(String url, HttpEntity reqEntity) throws IOException {
		HttpPut request = new HttpPut(url);
		addHeaders(request);
		request.setEntity(reqEntity);

		CloseableHttpClient client = createClicksignClient();

		return executeRequest(request, client);
	}

	private static HttpResponse executeDeleteRequest(String url, HttpEntity reqEntity) throws IOException {
		HttpDelete request = new HttpDelete(url);
		addHeaders(request);

		CloseableHttpClient client = createClicksignClient();

		return executeRequest(request, client);
	}

	private static HttpResponse executeRequest(HttpRequestBase request, CloseableHttpClient client) throws IOException {
		try {
			return client.execute(request);
		} catch (ClientProtocolException e) {
			throw new IOException(e);
		} catch (IOException e) {
			throw e;
		}
	}

	private static HttpEntity buildRequestEntity(Map<String, Object> params) throws ClicksignException {
		if (params == null) {
			return null;
		}

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof File) {
				builder.addPart(key, new FileBody((File) value));
			} else if (value instanceof SignatureList) {
				List<Signature> signatureList = ((SignatureList) value).getSignatures();

				for (int i = 0; i < signatureList.size(); i++) {
					Signature signature = signatureList.get(i);
					String mailKey = String.format("%s[][email]", key);
					StringBody email = new StringBody(signature.getEmail(), TEXT_PLAIN);
					builder.addPart(mailKey, email);

					String actKey = String.format("%s[][act]", key);
					StringBody act = new StringBody(signature.getAct(), TEXT_PLAIN);
					builder.addPart(actKey, act);
				}
			} else if (value instanceof List<?>) {
				List<?> nestedList = (List<?>) value;
				for (int i = 0; i < nestedList.size(); i++) {
					String nKey = String.format("%s[]", key);

					StringBody nValue;
					try {
						nValue = new StringBody(gson.toJson(nestedList.get(i), Map.class).toString(), TEXT_PLAIN);
					} catch (ClassCastException e) {
						nValue = new StringBody(nestedList.get(i).toString(), TEXT_PLAIN);
					}
					builder.addPart(nKey, nValue);
				}
			} else if (value instanceof String) {
				builder.addPart(key, new StringBody(value.toString(), TEXT_PLAIN));
			} else if (value instanceof Boolean) {
				builder.addPart(key, new StringBody(gson.toJson(value, Boolean.class).toString(), TEXT_PLAIN));
			} else {
				throw new ClicksignException(
						String.format("Erro ao codificar parametros HTTP: %s", value.getClass().getName()));
			}
		}

		return builder.build();
	}

	private static String formatFinalUrl(String url, String accessToken) {
		return String.format("%s?access_token=%s", url, accessToken);
	}

	private static CloseableHttpClient createClicksignClient() {
		return HttpClients.createDefault();
	}

	private static class Error {
		@SuppressWarnings("unused")
		String type;
		String message;
		String code;
		String param;
		String error;
	}

	private static void handleAPIError(String body, int code) throws ClicksignException {
		try {
			ClicksignResource.Error error = gson.fromJson(body, ClicksignResource.Error.class);

			if (error.error.length() > 0) {
				throw new ClicksignException(error.error);
			}

			throw new ClicksignException(error.message, error.param, null);
		} catch (Exception e) {
			throw new ClicksignException(
					String.format("An error occured. Response code: %s Response body: %s", code, body));
		}
	}
}
