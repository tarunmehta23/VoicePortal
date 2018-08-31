package com.charter.provisioning.voiceportal.handler;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.charter.provisioning.dsb.util.ASBMessageHandler;
import com.charter.provisioning.voiceportal.exception.ServiceException;
import com.charter.provisioning.voiceportal.http.HttpTransportHandler;
import com.charter.provisioning.voiceportal.util.MockObjectCreator;

@RunWith(MockitoJUnitRunner.class)
public class DSBMessageHandlerTest {

	@InjectMocks
	private DSBMessageHandler dsbMessageHandler;

	@Mock
	private HttpTransportHandler httpTransportHandler;

	@Mock
	private ASBMessageHandler asbMessageHandler;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void queryByCustomerGuid_MissingCustomerInfoRequest_ExpectsServiceException() {

		expectedEx.expect(ServiceException.class);
		expectedEx.expectMessage(String.format("Transaction id %s, DSB missing customer info %s.",
				MockObjectCreator.TRANS_ID, MockObjectCreator.missingCustInfoResponseAsASBMessage().toString()));

		when(httpTransportHandler.sendAndReceive(MockObjectCreator.TRANS_ID,
				MockObjectCreator.missingCustInfoDSBRequest()))
						.thenReturn(MockObjectCreator.missingCustInfoDSBResponse());

		dsbMessageHandler.queryByCustomerGuid(MockObjectCreator.TRANS_ID,
				MockObjectCreator.MISSING_CUSTOMER_INFO_CUSTOMER_GUID);
	}
	
	@Test
	public void queryByCustomerGuid_ValidRequest_ExpectsMap() {

		when(httpTransportHandler.sendAndReceive(MockObjectCreator.TRANS_ID,
				MockObjectCreator.validCustInfoDSBRequest()))
						.thenReturn(MockObjectCreator.validCustInfoDSBResponse());

		assertThat(dsbMessageHandler.queryByCustomerGuid(MockObjectCreator.TRANS_ID,
				MockObjectCreator.VALID_CUSTOMER_GUID), instanceOf(Map.class));
		
	}
	
	@Test
	public void queryByAccountGuid_MissingCustomerInfoRequest_ExpectsMap() {

		when(httpTransportHandler.sendAndReceive(MockObjectCreator.TRANS_ID,
				MockObjectCreator.validAccountInfoDSBRequest()))
						.thenReturn(MockObjectCreator.validAccountInfoDSBResponse());

		assertThat(dsbMessageHandler.queryByAccountGuid(MockObjectCreator.TRANS_ID,
				MockObjectCreator.VALID_ACCOUNT_GUID), instanceOf(Map.class));
		
	}
	
	@Test
	public void queryByAccountGuid_ValidRequest_ExpectsMap() {

		when(httpTransportHandler.sendAndReceive(MockObjectCreator.TRANS_ID,
				MockObjectCreator.missingAccountInfoDSBRequest()))
						.thenReturn(MockObjectCreator.missingAccountInfoDSBResponse());

		assertThat(dsbMessageHandler.queryByAccountGuid(MockObjectCreator.TRANS_ID,
				MockObjectCreator.MISSING_ACCOUNT_INFO_ACCOUNT_GUID), instanceOf(Map.class));
		
	}
}
