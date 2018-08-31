package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
public class BPSDPhoneService {

	private String type;
	private String phoneNumber;
	private String status;
	private String provider;
	private String context;
	private String imsDomain;
	private String category;
	private String subscriberId;
	private String callAgent;
	
	//Case issues
	@JacksonXmlProperty(localName = "PIC")
	private String PIC;
	
	//Case issues
	@JacksonXmlProperty(localName = "IPIC")
	private String IPIC;
	
	//Case issues
	@JacksonXmlProperty(localName = "LPIC")
	private String LPIC;
	
	private String role;
	private String displayName;
	private String rcDesc;
	private String rcState;
	
	@JsonProperty("TerminationProfile")
	@JacksonXmlProperty(localName = "TerminationProfile")
	private BPSDPhoneServiceTerminationProfile bpsDPhoneTerminationProfile;
	
	@JsonProperty("Features")
	@JacksonXmlProperty(localName = "Features")
	private BPSDPhoneServiceFeatures bpsDPhoneFeatures; 
	
}
