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
public class BPSBGroupServiceMembers {
	
	@JsonProperty("Member")
	@JacksonXmlProperty(localName = "Member")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BPSBGroupServiceMember> bpsBGroupMembers;
}
