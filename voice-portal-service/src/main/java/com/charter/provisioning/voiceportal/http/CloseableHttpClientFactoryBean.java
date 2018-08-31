package com.charter.provisioning.voiceportal.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CloseableHttpClientFactoryBean implements FactoryBean {

	protected PoolingHttpClientConnectionManager httpConnectionManager;

	@Value("${dsb.http.connectionManagerShared}")
	public boolean connectionManagerShared;

	@Value("${dsb.http.socketTimeout}")
	public int socketTimeout;

	@Value("${dsb.http.connectTimeout}")
	public int connectTimeout;

	@Value("${dsb.http.connectionRequestTimeout}")
	public int connectionRequestTimeout;

	@Value("${dsb.http.validateInactivityTimer}")
	public int validateInactivityTimer;

	@Autowired
	public CloseableHttpClientFactoryBean(HttpConnectionMgrFactoryBean httpConnectionMgrFactoryBean) throws Exception {
		this.httpConnectionManager = httpConnectionMgrFactoryBean.getObject();
	}

	/**
	 * Builds CloseableHttpClient.
	 * 
	 * @return CloseableHttpClient which was built using connectionManager 
	 */
	@Override
	public CloseableHttpClient getObject() throws Exception {

		log.info("Loaded properties...connectionManagerShared {}, socketTimeout {}, connectTimeout {}, connectionRequestTimeout {}, validateInactivityTimer {}",
				connectionManagerShared, socketTimeout, connectTimeout, connectionRequestTimeout,
				validateInactivityTimer);

		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
		RequestConfig requestConfig = requestConfigBuilder.setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectionRequestTimeout).build();
		Header reqHeader = new BasicHeader(HttpHeaders.CONNECTION, "keep-alive");
		List<Header> headers = new ArrayList<>();
		headers.add(reqHeader);

		httpConnectionManager.setValidateAfterInactivity(validateInactivityTimer);

		HttpClientBuilder builder = HttpClientBuilder.create().setConnectionManagerShared(connectionManagerShared)
				.setDefaultRequestConfig(requestConfig).setDefaultHeaders(headers)
				.setConnectionManager(httpConnectionManager);

		return builder.build();
	}

	@Override
	public Class<CloseableHttpClient> getObjectType() {
		return CloseableHttpClient.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
