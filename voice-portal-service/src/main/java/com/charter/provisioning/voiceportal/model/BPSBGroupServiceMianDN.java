package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSBGroupServiceMianDN {

	private String tcprk;
	private String cdp;
	private String prefix;
	private String vpnId;
	private String groupId;
	private String ext;
	
}
