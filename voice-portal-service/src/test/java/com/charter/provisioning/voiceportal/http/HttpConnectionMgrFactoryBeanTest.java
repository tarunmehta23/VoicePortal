package com.charter.provisioning.voiceportal.http;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.charter.provisioning.voiceportal.util.MockObjectCreator;

@RunWith(MockitoJUnitRunner.class)
public class HttpConnectionMgrFactoryBeanTest {

	@InjectMocks
	private HttpConnectionMgrFactoryBean httpConnectionMgrFactoryBean;

	@Before
	public void setUp() {
		httpConnectionMgrFactoryBean.maxTotalConnections = MockObjectCreator.MAX_TOTAL_CONNECTIONS;
		httpConnectionMgrFactoryBean.defaultMaxConnectionsPerRoute = MockObjectCreator.DEFAULT_MAX_CONNECTIONS_PER_ROUTE;
		httpConnectionMgrFactoryBean.validateAfterInactivity = MockObjectCreator.VALIDATE_AFTER_IN_ACTIVITY;
	}

	@Test
	public void getObject_ExpectsPoolingHttpClientConnectionManager() throws Exception{
		assertThat(httpConnectionMgrFactoryBean.getObject(), instanceOf(PoolingHttpClientConnectionManager.class));
	}
}
