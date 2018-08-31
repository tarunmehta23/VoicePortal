package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSDPhoneServiceFeaturesGSC2D {

	private String dn20;
	private String dn30;
	private String dn40;
}
