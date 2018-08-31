package com.charter.provisioning.voiceportal.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.charter.provisioning.dsb.util.DSBConstants;
import com.charter.provisioning.voiceportal.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HttpTransportHandler {

	private CloseableHttpClient httpClient;

	@Value("${dsb.url}")
	private String dsbUrl;

	@Autowired
	public HttpTransportHandler(CloseableHttpClientFactoryBean closeableHttpClientFactoryBean) throws Exception {
		this.httpClient = closeableHttpClientFactoryBean.getObject();
	}

	/**
	 * This method is to make a HTTP call to DSB.
	 * 
	 * @param transactionId TransactionId for this request.
	 * @param dsbRequest dsbRequest to get account info
	 * @return String consists dsb response
	 */
	public String sendAndReceive(String transactionId, String dsbRequest) {

		log.info("Transaction id {}, dsbUrl {}, dsbRequest {}", transactionId, dsbUrl, dsbRequest);

		StringBuilder dsbResponse = new StringBuilder();

		try {

			HttpPost httpPost = new HttpPost(dsbUrl);

			EntityBuilder builder = EntityBuilder.create().setContentType(ContentType.TEXT_XML).setText(dsbRequest);
			HttpEntity requestEntity = builder.build();

			httpPost.setEntity(requestEntity);

			try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {

				log.info("Transaction id {}, httpResponse {}", transactionId, httpResponse.toString());

				if (httpResponse.getStatusLine().getStatusCode() == DSBConstants.DSB_SUCCESS_CODE) {

					try (BufferedReader reader = new BufferedReader(
							new InputStreamReader(httpResponse.getEntity().getContent()))) {

						String inputLine;
						while ((inputLine = reader.readLine()) != null) {
							dsbResponse.append(inputLine);
						}
					}

				} else {
					log.error("Transaction id {}, httpResponse with no success code {}", transactionId,
							httpResponse.toString());

					throw new ServiceException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							String.format("Transaction id %s, httpResponse with no success code %s.", transactionId,
									httpResponse.toString()));

				}
			}

		} catch (IOException e) {
			log.error("Transaction id {}, HTTP related exception message {}", transactionId, e.getMessage());

			throw new ServiceException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, String
					.format("Transaction id %s, HTTP related exception message %s.", transactionId, e.getMessage()));

		}

		return dsbResponse.toString();
	}

}
