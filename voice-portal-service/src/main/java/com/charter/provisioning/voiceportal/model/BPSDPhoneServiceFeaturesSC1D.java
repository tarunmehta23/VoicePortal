package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSDPhoneServiceFeaturesSC1D {
	
	private String dn2;
	private String dn3;
	private String dn4;
	private String dn5;
	private String dn6;
	private String dn7;
	private String dn8;
	private String dn9;
	
}
