package com.charter.provisioning.voiceportal.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.charter.provisioning.voiceportal.config.EndpointClientConfig;
import com.charter.provisioning.voiceportal.exception.ServiceException;
import com.charter.provisioning.voiceportal.model.SpcAccountDivisionRequest;
import com.charter.provisioning.voiceportal.model.SpcAccountDivisionResponse;
import com.charter.provisioning.voiceportal.model.VoicePortalResponse;
import com.charter.provisioning.voiceportal.util.BPSRequestBuilder;
import com.charter.provisioning.voiceportal.util.ResponseParser;
import com.charter.provisioning.voiceportal.util.VoicePortalConstants;
import com.twc.bps.swebapi.Request;
import com.twc.bps.swebapi.RequestResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VoicePortalHandler {

	@Value("${spectrum-core.get-spc-account-division.url}")
	public String spectrumCoreUrl;

	@Value("${spectrum-core.get-spc-account-division.system-id}")
	public String systemId;

	private BPSRequestBuilder requestBuilder;

	private ResponseParser responseParser;

	private EndpointClientConfig clientConfig;

	private RestTemplate restTemplate;

	private DSBMessageHandler dsbMessageHandler;

	@Autowired
	public VoicePortalHandler(BPSRequestBuilder requestBuilder, ResponseParser responseParser,
			EndpointClientConfig clientConfig, RestTemplate restTemplate, DSBMessageHandler dsbMessageHandler) {
		this.requestBuilder = requestBuilder;
		this.responseParser = responseParser;
		this.clientConfig = clientConfig;
		this.restTemplate = restTemplate;
		this.dsbMessageHandler = dsbMessageHandler;
	}

	/**
	 * Return all Voice mail settings for all the telephones associated with
	 * requested telephoneNumber or accountGuid or customerGuid.
	 * 
	 * @param telephoneNumber Queried telephone Number to get VoiceMail Settings
	 * @param accountGUID Queried accountGUID to get VoiceMail Settings
	 * @param customerGUID Queried customerGUID to get VoiceMail Settings
	 * @param transactionId TransactionId for this request.
	 * @return List of Objects representing VMSettings
	 */
	public VoicePortalResponse getVoicemailSettings(String telephoneNumber, String accountGUID, String customerGUID,
			String transactionId) {

		validateVoicePortalRequest(telephoneNumber, accountGUID, customerGUID, transactionId);

		VoicePortalResponse response;
		if (StringUtils.isNotEmpty(telephoneNumber))
			response = getVoicemailSettingsByTN(telephoneNumber, transactionId);
		else if (StringUtils.isNotEmpty(accountGUID))
			response = getVoicemailSettingsByAccountGUID(accountGUID, transactionId);
		else
			response = getVoicemailSettingsByCustomerGUID(customerGUID, transactionId);

		return response;

	}

	private void validateVoicePortalRequest(String telephoneNumber, String accountGUID, String customerGUID,
			String transactionId) {

		if (StringUtils.isEmpty(telephoneNumber) && StringUtils.isEmpty(accountGUID)
				&& StringUtils.isEmpty(customerGUID)) {
			log.error(
					"Transaction id {}, Invalid request,  Telephone Number {} or Account GUID {} or Customer GUID {} must be populated",
					transactionId, telephoneNumber, accountGUID, customerGUID);
			throw new ServiceException(HttpServletResponse.SC_BAD_REQUEST, String.format(
					"Transaction id %s, Invalid request, Telephone Number %s or Account GUID %s or Customer GUID %s must be populated",
					transactionId, telephoneNumber, accountGUID, customerGUID));
		}

		// We should check only when Telephone Number not empty --> Telephone number
		// must be 10 digits, shouldn't start with 0
		if (StringUtils.isNotEmpty(telephoneNumber)
				&& !telephoneNumber.matches(VoicePortalConstants.TN_VALIDATION_EXP)) {
			log.error("Transaction id {}, Invalid telephone number {}", transactionId, telephoneNumber);
			throw new ServiceException(HttpServletResponse.SC_BAD_REQUEST,
					String.format("Transaction id %s, Invalid telephone number %s.", transactionId, telephoneNumber));
		}
	}

	private VoicePortalResponse getVoicemailSettingsByTN(String telephoneNumber, String transactionId) {

		Map<String, String> inputs = new HashMap<>();
		inputs.put(VoicePortalConstants.SCOPE, VoicePortalConstants.ALL);
		inputs.put(VoicePortalConstants.SERVICE_ID, telephoneNumber);

		HashSet<String> siteIds = getSiteId(telephoneNumber, transactionId);

		log.info("Transaction id {}, Site IDs {}", transactionId, siteIds);

		if (CollectionUtils.isEmpty(siteIds)) {
			log.error("Transaction id {}, Telephone number {} is not associated with a Spectrum Core account division.",
					transactionId, telephoneNumber);
			throw new ServiceException(HttpServletResponse.SC_NOT_FOUND, String.format(
					"Transaction id %s, Telephone number %s is not associated with a Spectrum Core account division.",
					transactionId, telephoneNumber));
		}

		VoicePortalResponse response = null;
		List<String> sitesWithNoBPSAssociation = new ArrayList<>();
		for (String siteId : siteIds) {

			log.info("Transaction id {}, Site ID {}", transactionId, siteId);

			clientConfig.setBPSUri(siteId);

			if (StringUtils.isEmpty(clientConfig.getBpsUri())) {
				log.error("Transaction id {}, Site ID is not associated with a BPS Instance: {}", transactionId,
						siteId);
				sitesWithNoBPSAssociation.add(siteId);
				continue;
			}

			response = sendAndReceiveBPSRequest(transactionId, inputs);

			/* If we have valid response return the response, else log and continue */
			if (response != null)
				return response;

			log.error("Transaction id {}, Site ID {}, Telephone number {} not found in BPS", transactionId, siteId,
					telephoneNumber);
		}

		/*
		 * We can't throw this exception in loop, because of multiple siteIds scenario
		 */
		if (response == null) {
			if (!CollectionUtils.isEmpty(sitesWithNoBPSAssociation)) {
				log.error("Transaction id {}, SiteId(s) {} is not associated with a BPS Instance", transactionId,
						sitesWithNoBPSAssociation);
				throw new ServiceException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						String.format("Transaction id %s, SiteId(s) %s is not associated with a BPS Instance.",
								transactionId, sitesWithNoBPSAssociation));
			} else {
				log.error("Transaction id {}, Telephone number {} not found in BPS", transactionId, telephoneNumber);
				throw new ServiceException(HttpServletResponse.SC_NOT_FOUND, String.format(
						"Transaction id %s, Telephone number %s not found in BPS.", transactionId, telephoneNumber));

			}
		}

		return response;

	}

	private VoicePortalResponse getVoicemailSettingsByAccountGUID(String accountGUID, String transactionId) {

		log.info("Transaction id {}, accountGUID {}", transactionId, accountGUID);

		Map<String, String> accountInfo = dsbMessageHandler.queryByAccountGuid(transactionId, accountGUID);

		if (CollectionUtils.isEmpty(accountInfo)) {
			log.error("Transaction id {}, accountGUID {}, accountInfo {}", transactionId, accountGUID, accountInfo);
			throw new ServiceException(HttpServletResponse.SC_NOT_FOUND,
					String.format("Transaction id %s, Account GUID %s not found in DSB.", transactionId, accountGUID));

		}

		VoicePortalResponse response = getVoicemailSettingsByAccountInfo(accountInfo, transactionId);

		/*
		 * If we have valid response return the response, else log and throw exception
		 */
		if (response != null)
			return response;

		log.error("Transaction id {}, Customer details not found in BPS for Account GUID {}", transactionId,
				accountGUID);
		throw new ServiceException(HttpServletResponse.SC_NOT_FOUND,
				String.format("Transaction id %s, Customer details not found in BPS for Account GUID %s", transactionId,
						accountGUID));

	}

	private VoicePortalResponse getVoicemailSettingsByCustomerGUID(String customerGUID, String transactionId) {

		log.info("Transaction id {}, customerGUID {}", transactionId, customerGUID);

		Map<String, String> accountInfo = dsbMessageHandler.queryByCustomerGuid(transactionId, customerGUID);

		if (CollectionUtils.isEmpty(accountInfo)) {
			log.error("Transaction id {}, customerGUID {}, accountInfo {}", transactionId, customerGUID, accountInfo);
			throw new ServiceException(HttpServletResponse.SC_NOT_FOUND, String
					.format("Transaction id %s, Customer GUID %s not found in DSB.", transactionId, customerGUID));

		}
		VoicePortalResponse response = getVoicemailSettingsByAccountInfo(accountInfo, transactionId);

		/*
		 * If we have valid response return the response, else log and throw exception
		 */
		if (response != null)
			return response;

		log.error("Transaction id {}, Customer details not found in BPS for Customer GUID {}", transactionId,
				customerGUID);
		throw new ServiceException(HttpServletResponse.SC_NOT_FOUND,
				String.format("Transaction id %s, Customer details not found in BPS for Customer GUID %s",
						transactionId, customerGUID));

	}

	private VoicePortalResponse sendAndReceiveBPSRequest(String transactionId, Map<String, String> inputs) {

		Request bpsRequest = requestBuilder.buildBPSRequest(inputs, "GetCustomerDetails");

		log.info("Transaction id {}, BPS request {}", transactionId,
				ReflectionToStringBuilder.toString(bpsRequest, new RecursiveToStringStyle()));

		RequestResponse bpsResponse = clientConfig.sendRequest(transactionId, bpsRequest);

		log.info("Transaction id {}, BPS response {}", transactionId,
				ReflectionToStringBuilder.toString(bpsResponse, new RecursiveToStringStyle()));

		return responseParser.parseBPSResponse(bpsResponse, transactionId);
	}

	private VoicePortalResponse getVoicemailSettingsByAccountInfo(Map<String, String> inputs, String transactionId) {

		clientConfig.setBPSUri(inputs.get(VoicePortalConstants.SITE_CODE));

		if (StringUtils.isEmpty(clientConfig.getBpsUri())) {
			log.error("Transaction id {}, Site ID is not associated with a BPS Instance: {}", transactionId,
					inputs.get(VoicePortalConstants.SITE_CODE));
			throw new ServiceException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					String.format("Transaction id %s, SiteId %s is not associated with a BPS Instance.", transactionId,
							inputs.get(VoicePortalConstants.SITE_CODE)));
		}

		VoicePortalResponse response = sendAndReceiveBPSRequest(transactionId, inputs);

		return response;
	}

	private HashSet<String> getSiteId(String telephoneNumber, String transactionId) {

		SpcAccountDivisionRequest spcAccountDivisionRequest = new SpcAccountDivisionRequest();
		spcAccountDivisionRequest.setTelephoneNumber(telephoneNumber);
		spcAccountDivisionRequest.setSystemID(systemId);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(spectrumCoreUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<SpcAccountDivisionRequest> entity = new HttpEntity<>(spcAccountDivisionRequest, headers);

		log.info("Transaction id {}, Spectrum Core Url {}, Request{}", transactionId, builder.toUriString(),
				spcAccountDivisionRequest.toString());

		ResponseEntity<SpcAccountDivisionResponse> response = restTemplate.exchange(builder.toUriString(),
				HttpMethod.POST, entity, SpcAccountDivisionResponse.class);

		log.info("Transaction id {}, Response:{}", transactionId, response.toString());

		return responseParser.getSiteId(response);
	}

}
