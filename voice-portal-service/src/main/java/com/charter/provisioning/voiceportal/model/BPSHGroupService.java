package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSHGroupService {

	private String type;
	private String category;
	private String huntType;
	private String pilotPhoneNumber;
	
	@JsonProperty("Members")
	@JacksonXmlProperty(localName = "Members")
	private BPSHGroupServiceMembers bpsHGroupServiceMembers;
		
}
