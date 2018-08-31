package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSDPhoneServiceFeaturesBLOCK {

	@JsonProperty("900")
	@JacksonXmlProperty(localName = "_900")
	private String _900;
	
	@JsonProperty("976")
	@JacksonXmlProperty(localName = "_976")
	private String _976;
	
	private String INTL;
	
	@JsonProperty("0+")
	@JacksonXmlProperty(localName = "_0PLUS")
	private String _0PLUS;
	
	private String DA;
	private String CASUAL;
	
	@JsonProperty("411")
	@JacksonXmlProperty(localName = "_411")
	private String _411;
	
	private String NDA;
	private String IBR;
	private String OBR;
	private String ACC;
}
