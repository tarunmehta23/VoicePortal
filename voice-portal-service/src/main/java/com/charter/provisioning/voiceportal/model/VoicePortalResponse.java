package com.charter.provisioning.voiceportal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class VoicePortalResponse {

	private String accountNumber;
	private String siteCode;
	private String status;
	private String firstName;
	private String lastName;
	private String contactPhoneNumber;
	private String createDate;
	private String updateDate;
	private String regState;
	private String regStateLastUpdated;
	private String regStateReason;
	private String regStateReasonLastUpdated;
	private String serviceCodes;
	
	@JsonProperty("Address")
	@JacksonXmlProperty(localName = "Address")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BPSAddress> addresses;
	
	@JsonProperty("Device")
	@JacksonXmlProperty(localName = "Device")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BPSDevice> devices;
	
	@JsonProperty("User")
	@JacksonXmlProperty(localName = "User")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BPSUser> users;
	
	@JsonProperty("VMailService")
	@JacksonXmlProperty(localName = "VMailService")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BPSVMailService> vmailServices;
	
	@JsonProperty("HGroupService")
	@JacksonXmlProperty(localName = "HGroupService")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BPSHGroupService> hgroupServices;
	
	@JsonProperty("BGroupService")
	@JacksonXmlProperty(localName = "BGroupService")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BPSBGroupService> bgroupServices;
	
	@JsonProperty("DPhoneService")
	@JacksonXmlProperty(localName = "DPhoneService")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BPSDPhoneService> dphoneServices;
	
	@JsonProperty("AGroupService")
	@JacksonXmlProperty(localName = "AGroupService")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BPSAGroupService> agroupService;
	
}
