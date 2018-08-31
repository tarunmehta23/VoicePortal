package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSAGroupService {
	
	private String type;
	private String id;
	private String category;
	private String coverphonenumber;
	private String phoneNumber;
	
	@JsonProperty("Attendants")
	@JacksonXmlProperty(localName = "Attendants")
	private BPSAGroupServiceAttendants bpsAGroupServiceAttendants;
	
	@JsonProperty("Members")
	@JacksonXmlProperty(localName = "Members")
	private BPSAGroupServiceMembers bpsAGroupServiceMembers;
}

