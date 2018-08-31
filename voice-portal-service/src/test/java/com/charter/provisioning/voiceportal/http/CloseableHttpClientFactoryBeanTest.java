package com.charter.provisioning.voiceportal.http;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.charter.provisioning.voiceportal.util.MockObjectCreator;

@RunWith(MockitoJUnitRunner.class)
public class CloseableHttpClientFactoryBeanTest {

	@InjectMocks
	private CloseableHttpClientFactoryBean closeableHttpClientFactoryBean;
	
	@Mock
	private HttpConnectionMgrFactoryBean httpConnectionMgrFactoryBean;
	
	@Mock
	private PoolingHttpClientConnectionManager httpConnectionManager;
	
	@Before
	public void setUp() throws Exception {
		
		closeableHttpClientFactoryBean.httpConnectionManager = httpConnectionManager;
		
		closeableHttpClientFactoryBean.connectionManagerShared = MockObjectCreator.CONNECTION_MANAGER_SHARED;
		closeableHttpClientFactoryBean.socketTimeout = MockObjectCreator.SOCKET_TIMEOUT;
		closeableHttpClientFactoryBean.connectTimeout = MockObjectCreator.CONNECTION_TIMEOUT;
		closeableHttpClientFactoryBean.connectionRequestTimeout = MockObjectCreator.CONNECTION_REQUEST_TIMEOUT;
		closeableHttpClientFactoryBean.validateInactivityTimer = MockObjectCreator.VALIDATE_IN_ACTIVITY_TIMER;
		
	}
	
	@Test
	public void getObject_ExpectsCloseableHttpClient() throws Exception {
		assertThat(closeableHttpClientFactoryBean.getObject(), instanceOf(CloseableHttpClient.class));
	}
	
}
