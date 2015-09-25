package com.clicksign.net;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.clicksign.Clicksign;
import com.clicksign.exception.ClicksignException;

public class ClicksignResponse {
	int code;
	String body;
	HttpResponse httpResponse;

	public ClicksignResponse(int responseCode, String responseBody) {
		this.code = responseCode;
		this.body = responseBody;
	}

	public ClicksignResponse(HttpResponse httpResponse) throws ClicksignException {
		int rCode = httpResponse.getStatusLine().getStatusCode();
		HttpEntity entity = httpResponse.getEntity();
		String rBody = null;
		try {
			rBody = entity != null ? EntityUtils.toString(entity) : null;
		} catch (ParseException e) {
			throw new ClicksignException(String.format("Erro ao ler a resposta da Clicksign (%s)", Clicksign.API_BASE),
					e);
		} catch (IOException e) {
			throw new ClicksignException(
					String.format("Não foi possível obter a resposta da Clicksign (%s)", Clicksign.API_BASE), e);
		}
		this.setHttpResponse(httpResponse);
		this.setCode(rCode);
		this.setBody(rBody);
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int responseCode) {
		this.code = responseCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String responseBody) {
		this.body = responseBody;
	}

}
