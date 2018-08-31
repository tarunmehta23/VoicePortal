package com.charter.provisioning.voiceportal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
public class BPSAGroupServiceAttendants {

	@JsonProperty("Attendant")
	@JacksonXmlProperty(localName = "Attendant")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BPSAGroupServiceAttendant> bpsAGroupServiceAttendant;
}
