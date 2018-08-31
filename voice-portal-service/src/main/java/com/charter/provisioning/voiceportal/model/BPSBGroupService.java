package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSBGroupService {

	private String type;
	private String phoneNumber;
	private String category;
	private String subscriberId;
	
	@JsonProperty("MainDN")
	@JacksonXmlProperty(localName = "MainDN")
	private BPSBGroupServiceMianDN bpsBGroupMianDN;
	
	@JsonProperty("Members")
	@JacksonXmlProperty(localName = "Members")
	private BPSBGroupServiceMembers bpsBGroupMembers;
	
}

