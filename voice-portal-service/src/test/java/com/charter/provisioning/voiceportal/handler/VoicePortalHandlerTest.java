package com.charter.provisioning.voiceportal.handler;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.charter.provisioning.voiceportal.config.EndpointClientConfig;
import com.charter.provisioning.voiceportal.exception.ServiceException;
import com.charter.provisioning.voiceportal.model.SpcAccountDivisionResponse;
import com.charter.provisioning.voiceportal.util.BPSRequestBuilder;
import com.charter.provisioning.voiceportal.util.MockObjectCreator;
import com.charter.provisioning.voiceportal.util.ResponseParser;

@RunWith(MockitoJUnitRunner.class)
public class VoicePortalHandlerTest {

	@InjectMocks
	private VoicePortalHandler voicePortalHandler;

	@Mock
	private BPSRequestBuilder requestBuilder;

	@Mock
	private ResponseParser responseParser;

	@Mock
	private EndpointClientConfig clientConfig;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private DSBMessageHandler dsbMessageHandler;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() {
		voicePortalHandler.spectrumCoreUrl = MockObjectCreator.SPECTRUM_CORE_URL;
		voicePortalHandler.systemId = MockObjectCreator.SYSTEM_ID;
	}

	@Test
	public void getVoicemailSettings_InvalidRequest_ExpectsServiceException() {

		expectedEx.expect(ServiceException.class);
		expectedEx.expectMessage(String.format(
				"Transaction id %s, Invalid request, Telephone Number %s or Account GUID %s or Customer GUID %s must be populated",
				MockObjectCreator.TRANS_ID, MockObjectCreator.EMPTY, MockObjectCreator.EMPTY, MockObjectCreator.EMPTY));

		voicePortalHandler.getVoicemailSettings(MockObjectCreator.EMPTY, MockObjectCreator.EMPTY,
				MockObjectCreator.EMPTY, MockObjectCreator.TRANS_ID);
	}

	@Test
	public void getVoicemailSettings_InvalidTN_ExpectsServiceException() {

		expectedEx.expect(ServiceException.class);
		expectedEx.expectMessage("Invalid telephone number");

		voicePortalHandler.getVoicemailSettings(MockObjectCreator.INVALID_TN, MockObjectCreator.EMPTY,
				MockObjectCreator.EMPTY, MockObjectCreator.TRANS_ID);
	}

	@Test
	public void getVoicemailSettings_NotInSpectrumCoreTN_ExpectsServiceException() {

		expectedEx.expect(ServiceException.class);
		expectedEx.expectMessage(String.format(
				"Transaction id %s, Telephone number %s is not associated with a Spectrum Core account division.",
				MockObjectCreator.TRANS_ID, MockObjectCreator.NOTIN_SPECTRUM_CORE_TN));

		when(restTemplate.exchange(MockObjectCreator.SPECTRUM_CORE_URL, HttpMethod.POST,
				MockObjectCreator.spectrumCoreRequest(MockObjectCreator.NOTIN_SPECTRUM_CORE_TN),
				SpcAccountDivisionResponse.class)).thenReturn(MockObjectCreator.inValidSpectrumCoreResponse());
		voicePortalHandler.getVoicemailSettings(MockObjectCreator.NOTIN_SPECTRUM_CORE_TN, MockObjectCreator.EMPTY,
				MockObjectCreator.EMPTY, MockObjectCreator.TRANS_ID);
	}

	@Test
	public void getVoicemailSettings_DivisionNotTied_ExpectsServiceException() {

		expectedEx.expect(ServiceException.class);
		expectedEx.expectMessage(String.format("Transaction id %s, SiteId(s) %s is not associated with a BPS Instance.",
				MockObjectCreator.TRANS_ID, MockObjectCreator.DIVISION_IDS));

		when(restTemplate.exchange(MockObjectCreator.SPECTRUM_CORE_URL, HttpMethod.POST,
				MockObjectCreator.spectrumCoreRequest(MockObjectCreator.VALID_TN), SpcAccountDivisionResponse.class))
						.thenReturn(MockObjectCreator.validSpectrumCoreResponse());
		when(responseParser.getSiteId(MockObjectCreator.validSpectrumCoreResponse()))
				.thenReturn(MockObjectCreator.DIVISION_IDS);
		when(clientConfig.getBpsUri()).thenReturn(MockObjectCreator.EMPTY);

		voicePortalHandler.getVoicemailSettings(MockObjectCreator.VALID_TN, MockObjectCreator.EMPTY,
				MockObjectCreator.EMPTY, MockObjectCreator.TRANS_ID);
	}

	@Test
	public void getVoicemailSettings_NotInBpsTN_ExpectsServiceException() {

		expectedEx.expect(ServiceException.class);
		expectedEx.expectMessage(String.format("Transaction id %s, Telephone number %s not found in BPS.",
				MockObjectCreator.TRANS_ID, MockObjectCreator.NOTIN_BPS_TN));

		when(restTemplate.exchange(MockObjectCreator.SPECTRUM_CORE_URL, HttpMethod.POST,
				MockObjectCreator.spectrumCoreRequest(MockObjectCreator.NOTIN_BPS_TN),
				SpcAccountDivisionResponse.class)).thenReturn(MockObjectCreator.validSpectrumCoreResponse());
		when(responseParser.getSiteId(MockObjectCreator.validSpectrumCoreResponse()))
				.thenReturn(MockObjectCreator.DIVISION_IDS);
		when(clientConfig.getBpsUri()).thenReturn(MockObjectCreator.BPS_URL);
		when(requestBuilder.buildBPSRequest(MockObjectCreator.notInBPSRequestInputs(), "GetCustomerDetails"))
				.thenReturn(MockObjectCreator.bpsRequest(MockObjectCreator.notInBPSRequestInputs()));
		when(clientConfig.sendRequest(MockObjectCreator.TRANS_ID,
				MockObjectCreator.bpsRequest(MockObjectCreator.notInBPSRequestInputs())))
						.thenReturn(MockObjectCreator.notInBPSResponse());

		voicePortalHandler.getVoicemailSettings(MockObjectCreator.NOTIN_BPS_TN, MockObjectCreator.EMPTY,
				MockObjectCreator.EMPTY, MockObjectCreator.TRANS_ID);
	}

	@Test
	public void getVoicemailSettings_NotInDSBAccountGUID_ExpectsServiceException() throws Exception {

		expectedEx.expect(ServiceException.class);
		expectedEx.expectMessage(String.format("Transaction id %s, Account GUID %s not found in DSB.",
				MockObjectCreator.TRANS_ID, MockObjectCreator.NOTIN_DSB_ACCOUNT_GUID));

		when(dsbMessageHandler.queryByAccountGuid(MockObjectCreator.TRANS_ID, MockObjectCreator.NOTIN_DSB_ACCOUNT_GUID))
						.thenReturn(MockObjectCreator.inValidDSBResponse());

		voicePortalHandler.getVoicemailSettings(MockObjectCreator.EMPTY, MockObjectCreator.NOTIN_DSB_ACCOUNT_GUID,
				MockObjectCreator.EMPTY, MockObjectCreator.TRANS_ID);
	}

	@Test
	public void getVoicemailSettings_NotInBPSAccountGUID_ExpectsServiceException() throws Exception {

		expectedEx.expect(ServiceException.class);
		expectedEx
				.expectMessage(String.format("Transaction id %s, Customer details not found in BPS for Account GUID %s",
						MockObjectCreator.TRANS_ID, MockObjectCreator.NOTIN_BPS_ACCOUNT_GUID));

		when(dsbMessageHandler.queryByAccountGuid(MockObjectCreator.TRANS_ID, MockObjectCreator.NOTIN_BPS_ACCOUNT_GUID))
						.thenReturn(MockObjectCreator.validDSBResponse());
		when(clientConfig.getBpsUri()).thenReturn(MockObjectCreator.BPS_URL);
		when(requestBuilder.buildBPSRequest(MockObjectCreator.validDSBResponse(), "GetCustomerDetails"))
				.thenReturn(MockObjectCreator.bpsRequest(MockObjectCreator.validDSBResponse()));
		when(clientConfig.sendRequest(MockObjectCreator.TRANS_ID,
				MockObjectCreator.bpsRequest(MockObjectCreator.validDSBResponse())))
						.thenReturn(MockObjectCreator.notInBPSResponse());

		voicePortalHandler.getVoicemailSettings(MockObjectCreator.EMPTY, MockObjectCreator.NOTIN_BPS_ACCOUNT_GUID,
				MockObjectCreator.EMPTY, MockObjectCreator.TRANS_ID);
	}

	@Test
	public void getVoicemailSettings_NotInDSBCustomerGUID_ExpectsServiceException() throws Exception {

		expectedEx.expect(ServiceException.class);
		expectedEx.expectMessage(String.format("Transaction id %s, Customer GUID %s not found in DSB.",
				MockObjectCreator.TRANS_ID, MockObjectCreator.NOTIN_DSB_CUSTOMER_GUID));

		when(dsbMessageHandler.queryByCustomerGuid(MockObjectCreator.TRANS_ID, MockObjectCreator.NOTIN_DSB_CUSTOMER_GUID))
						.thenReturn(MockObjectCreator.inValidDSBResponse());

		voicePortalHandler.getVoicemailSettings(MockObjectCreator.EMPTY, MockObjectCreator.EMPTY,
				MockObjectCreator.NOTIN_DSB_CUSTOMER_GUID, MockObjectCreator.TRANS_ID);
	}

	@Test
	public void getVoicemailSettings_NotInBPSCustomerGUID_ExpectsServiceException() throws Exception {

		expectedEx.expect(ServiceException.class);
		expectedEx.expectMessage(
				String.format("Transaction id %s, Customer details not found in BPS for Customer GUID %s",
						MockObjectCreator.TRANS_ID, MockObjectCreator.NOTIN_BPS_CUSTOMER_GUID));

		when(dsbMessageHandler.queryByCustomerGuid(MockObjectCreator.TRANS_ID, MockObjectCreator.NOTIN_BPS_CUSTOMER_GUID))
						.thenReturn(MockObjectCreator.validDSBResponse());
		when(clientConfig.getBpsUri()).thenReturn(MockObjectCreator.BPS_URL);
		when(requestBuilder.buildBPSRequest(MockObjectCreator.validDSBResponse(), "GetCustomerDetails"))
				.thenReturn(MockObjectCreator.bpsRequest(MockObjectCreator.validDSBResponse()));
		when(clientConfig.sendRequest(MockObjectCreator.TRANS_ID,
				MockObjectCreator.bpsRequest(MockObjectCreator.validDSBResponse())))
						.thenReturn(MockObjectCreator.notInBPSResponse());

		voicePortalHandler.getVoicemailSettings(MockObjectCreator.EMPTY, MockObjectCreator.EMPTY,
				MockObjectCreator.NOTIN_BPS_CUSTOMER_GUID, MockObjectCreator.TRANS_ID);
	}

}
