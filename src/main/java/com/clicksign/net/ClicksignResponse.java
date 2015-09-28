package com.clicksign.net;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.clicksign.Clicksign;
import com.clicksign.exception.ClicksignException;

public class ClicksignResponse {
	int code;
	String body;
	InputStream content;

	public ClicksignResponse(int responseCode, String responseBody) {
		this.code = responseCode;
		this.body = responseBody;
	}

	public ClicksignResponse(HttpResponse httpResponse) throws ClicksignException {
		int code = httpResponse.getStatusLine().getStatusCode();
		setCode(code);

		HttpEntity entity = httpResponse.getEntity();
		setBodyOrContent(entity);
	}

	private void setBodyOrContent(HttpEntity entity) throws ClicksignException {
		String contentType = entity.getContentType().getValue();

		try {
			if (contentType.startsWith("application/json")) {
				this.setBody(getJsonBody(entity));
			} else if (contentType.startsWith("application/zip")) {
				this.setContent(entity.getContent());
			} else {
				throw new ClicksignException(String.format(
						"Tipo de conteúdo não esperado %s. Entre em contato com a Clicksign via suporte@clicksign.com",
						contentType));
			}
		} catch (ParseException e) {
			throw new ClicksignException(String.format("Erro ao ler a resposta da Clicksign (%s)", Clicksign.endPoint),
					e);
		} catch (IOException e) {
			throw new ClicksignException(
					String.format("Não foi possível obter a resposta da Clicksign (%s)", Clicksign.endPoint), e);
		}

	}

	private String getJsonBody(HttpEntity entity) throws ParseException, IOException {
		return entity != null ? EntityUtils.toString(entity) : null;
	}

	public InputStream getContent() {
		return content;
	}

	public void setContent(InputStream content) {
		this.content = content;
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