package com.charter.provisioning.voiceportal.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.charter.provisioning.voiceportal.model.BPSInstance;
import com.charter.web.services.endpoint.SoapClientProxyBuilder;
import com.twc.bps.swebapi.Request;
import com.twc.bps.swebapi.RequestResponse;
import com.twc.bps.swebapi.WebAPIServiceSoap;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Data
@ConfigurationProperties(prefix = "bps")
public class EndpointClientConfig {

	private List<BPSInstance> instances = new ArrayList<BPSInstance>();

	private String bpsUri;
	private String user;
	private String password;

	/* We can't define this in handler, causing unresolvable circular reference? */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/**
	 * Sends request to BPS and return the response.
	 * 
	 * @param bpsRequest BPS request
	 * @param transactionId TransactionId for this request.
	 * @return Returns BPSResponse
	 */
	public RequestResponse sendRequest(String transactionId, Request bpsRequest) {
		return getVoicePortalService(transactionId).request(bpsRequest);
	}
	
	/**
	 * JaxWs Proxy builder
	 * 
	 * @param transactionId TransactionId for this request.
	 * @return Returns WebApiServiceSoap interface
	 */
	public WebAPIServiceSoap getVoicePortalService(String transactionId) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(bpsUri);

		log.info("Transaction id {}, BPS Url {}, User {}", transactionId, builder.toUriString(), user);

		Logger inLogger = LoggerFactory.getLogger("VoicePortalService.client-proxy-in");
		Logger outLogger = LoggerFactory.getLogger("VoicePortalService.client-proxy-out");

		return SoapClientProxyBuilder
				.<WebAPIServiceSoap>createJaxWsProxyBuilder(WebAPIServiceSoap.class, builder.toUriString())
				.withOutboundLogging(outLogger).withInboundLogging(inLogger)
				.withUserNameTokenAuthentication(user, password).create();
	}

	/**
	 * Setting bpsUri, user and password for siteId
	 * 
	 * @param siteId SiteId.
	 */
	public void setBPSUri(String siteId) {

		bpsUri = null;
		user = null;
		password = null;

		for (BPSInstance bpsInstance : instances) {
			if (!CollectionUtils.isEmpty(bpsInstance.getSiteIds()) && bpsInstance.getSiteIds().contains(siteId)) {
				bpsUri = bpsInstance.getWebApiUrl();
				user = bpsInstance.getUser();
				password = bpsInstance.getPassword();
			}
		}
	}

}
