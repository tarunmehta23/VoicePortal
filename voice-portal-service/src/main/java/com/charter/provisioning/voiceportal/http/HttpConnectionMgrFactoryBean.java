package com.charter.provisioning.voiceportal.http;

import javax.net.ssl.SSLContext;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HttpConnectionMgrFactoryBean implements FactoryBean {

	@Value("${dsb.http.maxTotalConnections}")
	public int maxTotalConnections;

	@Value("${dsb.http.defaultMaxConnectionsPerRoute}")
	public int defaultMaxConnectionsPerRoute;

	@Value("${dsb.http.validateAfterInactivity}")
	public int validateAfterInactivity;

	/**
	 * Builds PoolingHttpClientConnectionManager.
	 * 
	 * @return PoolingHttpClientConnectionManager which was built using given SSL Context 
	 */
	@Override
	public PoolingHttpClientConnectionManager getObject() throws Exception {

		log.info("Loaded properties...maxTotalConnections {}, defaultMaxConnectionsPerRoute {}, validateInactivityTimer {}",
				maxTotalConnections, defaultMaxConnectionsPerRoute, validateAfterInactivity);

		NoopHostnameVerifier noopVerifier = new NoopHostnameVerifier();

		SSLContext sslContext = SSLContext.getDefault();
		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, noopVerifier);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
				.build();

		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connMgr.setDefaultMaxPerRoute(defaultMaxConnectionsPerRoute);
		connMgr.setMaxTotal(maxTotalConnections);
		connMgr.setValidateAfterInactivity(validateAfterInactivity);

		return connMgr;
	}

	@Override
	public Class<PoolingHttpClientConnectionManager> getObjectType() {
		return PoolingHttpClientConnectionManager.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
