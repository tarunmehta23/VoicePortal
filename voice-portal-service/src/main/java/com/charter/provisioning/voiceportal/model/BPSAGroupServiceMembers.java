package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
class BPSAGroupServiceMembers {

	@JsonProperty("Member")
	@JacksonXmlProperty(localName = "Member")
	@JacksonXmlElementWrapper(useWrapping = false)
	private BPSAGroupServiceMember bpsAGroupServiceMember;
}
