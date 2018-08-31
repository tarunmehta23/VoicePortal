package com.charter.provisioning.voiceportal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.charter.provisioning.dsb.model.ASBMessage;
import com.charter.provisioning.dsb.util.ASBMessageHandler;
import com.charter.provisioning.voiceportal.model.VoicePortalResponse;
import com.charter.provisioning.voiceportal.model.SpcAccountDivisionList;
import com.charter.provisioning.voiceportal.model.SpcAccountDivisionRequest;
import com.charter.provisioning.voiceportal.model.SpcAccountDivisionResponse;
import com.twc.bps.swebapi.Request;
import com.twc.bps.swebapi.RequestResponse;

public class MockObjectCreator {

	public static String TRANS_ID = "transId12345";

	public static String EMPTY = "";

	public static String AUDIT_USER = "pid12345";

	public static String VALID_TN = "5129616789";

	public static String INVALID_TN = "512961678";

	public static String NOTIN_BPS_TN = "1234567890";

	public static String NOTIN_SPECTRUM_CORE_TN = "1000000000";

	public static String SPECTRUM_CORE_URL = "spectrumcoremockurl.com";

	public static String BPS_URL = "bpsmockurl.com";

	public static String DSB_URL = "dsbmockurl.com";

	public static String SYSTEM_ID = "Gateway";

	public static String VALID_CUSTOMER_GUID = "FEBD50D5-CF30-A9FD-DD9E-CB9C50C229E3";

	public static String NOTIN_DSB_CUSTOMER_GUID = "AAAAAAA-AAA-AAA-AAA-AAAAAAAAAAAAA";
	
	public static String MISSING_CUSTOMER_INFO_CUSTOMER_GUID = "CCCCCCC-AAA-AAA-AAA-AAAAAAAAAAAAA";
	
	public static String MISSING_ACCOUNT_INFO_CUSTOMER_GUID = "DDDDDDD-AAA-AAA-AAA-AAAAAAAAAAAAA";
	
	public static String NOTIN_BPS_CUSTOMER_GUID = "BBBBBBBB-AAA-AAA-AAA-AAAAAAAAAAAAA";

	public static String VALID_ACCOUNT_GUID = "FC9E82F0-8C5C-B490-2341-42DF5FBB7967";

	public static String NOTIN_DSB_ACCOUNT_GUID = "BBBBBBBB-BBB-BBB-BBB-BBBBBBBBB";
	
	public static String MISSING_ACCOUNT_INFO_ACCOUNT_GUID = "EEEEEEE-AAA-AAA-AAA-AAAAAAAAAAAAA";

	public static String NOTIN_BPS_ACCOUNT_GUID = "CCCCCCCC-BBB-BBB-BBB-BBBBBBBBB";

	public static HashSet<String> DIVISION_IDS = new HashSet<>(Arrays.asList("CHTR", "CTX"));

	public static List<String> SITES_NO_BPS_ASSOCIATION = new ArrayList<>(Arrays.asList("CTX"));
	
	public static int MAX_TOTAL_CONNECTIONS = 20;
	
	public static int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 2;
	
	public static int VALIDATE_AFTER_IN_ACTIVITY = 300000;
	
	public static boolean CONNECTION_MANAGER_SHARED = false;
	
	public static int SOCKET_TIMEOUT = -1;
	
	public static int CONNECTION_TIMEOUT = -1;
	
	public static int CONNECTION_REQUEST_TIMEOUT = -1;
	
	public static int VALIDATE_IN_ACTIVITY_TIMER = 3000;
	
	public static MockHttpServletRequest getHttpServletRequest() {
		return new MockHttpServletRequest();
	}

	public static MockHttpServletResponse getHttpServletResponse() {
		return new MockHttpServletResponse();
	}

	public static HttpEntity<SpcAccountDivisionRequest> spectrumCoreRequest(String TN) {

		SpcAccountDivisionRequest request = new SpcAccountDivisionRequest();
		request.setTelephoneNumber(TN);
		request.setSystemID(SYSTEM_ID);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return new HttpEntity<>(request, headers);
	}

	public static ResponseEntity<SpcAccountDivisionResponse> inValidSpectrumCoreResponse() {

		SpcAccountDivisionResponse response = new SpcAccountDivisionResponse();

		List<SpcAccountDivisionList> spcAccountDivisionList = new ArrayList<>();
		SpcAccountDivisionList spcAccountDivision = new SpcAccountDivisionList();
		spcAccountDivisionList.add(spcAccountDivision);

		response.setSpcAccountDivisionList(spcAccountDivisionList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public static ResponseEntity<SpcAccountDivisionResponse> validSpectrumCoreResponse() {

		SpcAccountDivisionResponse response = new SpcAccountDivisionResponse();

		List<SpcAccountDivisionList> spcAccountDivisionList = new ArrayList<>();
		SpcAccountDivisionList spcAccountDivision = new SpcAccountDivisionList();
		spcAccountDivision.setAccountStatus("Active");
		spcAccountDivision.setDivisionID("CTX.001");
		spcAccountDivisionList.add(spcAccountDivision);

		response.setSpcAccountDivisionList(spcAccountDivisionList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public static Map<String, String> notInBPSRequestInputs() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put(VoicePortalConstants.SCOPE, VoicePortalConstants.ALL);
		inputs.put(VoicePortalConstants.SERVICE_ID, NOTIN_BPS_TN);
		return inputs;
	}

	public static Request bpsRequest(Map<String, String> inputs) {

		BPSRequestBuilder requestBuilder = new BPSRequestBuilder();
		return requestBuilder.buildBPSRequest(inputs, "GetCustomerDetails");
	}

	public static RequestResponse notInBPSResponse() {
		return new RequestResponse();
	}

	public static Map<String, String> inValidDSBResponse() {
		return new HashMap<>();
	}

	public static Map<String, String> validDSBResponse() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put(VoicePortalConstants.SITE_CODE, "CTX");
		inputs.put(VoicePortalConstants.ACCOUNT_NUMBER, "123456789");
		return inputs;
	}

	public static VoicePortalResponse validVoiceMailSettingsResponse() {

		VoicePortalResponse bpsAccount = new VoicePortalResponse();

		return bpsAccount;

	}

	public static String validCustInfoDSBRequest() {
		ASBMessageHandler asbMessageHandler = new ASBMessageHandler();
		return asbMessageHandler.buildCustomerGuidRequest(TRANS_ID, VALID_CUSTOMER_GUID);
	}
	
	public static String validCustInfoDSBResponse() {
		return "<ASBMessage timestamp='2018-05-16T17:30:55.651' version='2.0' id='32d6994f9fd14a06b5ede3643a9c9903'><Header> </Header> <Body items='1'><Customer ><Account><ID ns='ESB' value='sincodev4'/><Division id='DV2'/> </Account></Customer>  </Body> </ASBMessage>";
	}
	
	public static ASBMessage validCustInfoResponseAsASBMessage() {
		ASBMessageHandler asbMessageHandler = new ASBMessageHandler();
		return asbMessageHandler.buildASBMessageResponse(TRANS_ID, validCustInfoDSBResponse());
	}
	
	public static String missingCustInfoDSBRequest() {
		ASBMessageHandler asbMessageHandler = new ASBMessageHandler();
		return asbMessageHandler.buildCustomerGuidRequest(TRANS_ID, MISSING_CUSTOMER_INFO_CUSTOMER_GUID);
	}

	public static String missingCustInfoDSBResponse() {
		return "<ASBMessage timestamp='2018-05-16T17:30:55.651' version='2.0' id='32d6994f9fd14a06b5ede3643a9c9903'><Header> </Header> <Body items='1'> </Body> </ASBMessage>";
	}
	
	public static ASBMessage missingCustInfoResponseAsASBMessage() {
		ASBMessageHandler asbMessageHandler = new ASBMessageHandler();
		return asbMessageHandler.buildASBMessageResponse(TRANS_ID, missingCustInfoDSBResponse());
	}
	
	public static String validAccountInfoDSBRequest() {
		ASBMessageHandler asbMessageHandler = new ASBMessageHandler();
		return asbMessageHandler.buildAccountGuidRequest(TRANS_ID, VALID_ACCOUNT_GUID);
	}
	
	public static String validAccountInfoDSBResponse() {
		return "<ASBMessage timestamp='2018-05-16T17:30:55.651' version='2.0' id='32d6994f9fd14a06b5ede3643a9c9903'><Header> </Header> <Body items='1'><Account><ID ns='ESB' value='sincodev4'/><Division id='DV2'/> </Account></Body> </ASBMessage>";
	}
	
	public static ASBMessage validAccountInfoResponseAsASBMessage() {
		ASBMessageHandler asbMessageHandler = new ASBMessageHandler();
		return asbMessageHandler.buildASBMessageResponse(TRANS_ID, validAccountInfoDSBResponse());
	}
	
	public static String missingAccountInfoDSBRequest() {
		ASBMessageHandler asbMessageHandler = new ASBMessageHandler();
		return asbMessageHandler.buildAccountGuidRequest(TRANS_ID, MISSING_ACCOUNT_INFO_ACCOUNT_GUID);
	}
	
	public static String missingAccountInfoDSBResponse() {
		return "<ASBMessage timestamp='2018-05-16T17:30:55.651' version='2.0' id='32d6994f9fd14a06b5ede3643a9c9903'><Header> </Header> <Body items='1'></Body> </ASBMessage>";
	}
	
	public static ASBMessage missingAccountInfoResponseAsASBMessage() {
		ASBMessageHandler asbMessageHandler = new ASBMessageHandler();
		return asbMessageHandler.buildASBMessageResponse(TRANS_ID, missingAccountInfoDSBResponse());
	}

}
