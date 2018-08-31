package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSAddress {

	private String addrLine1;
	private String addrCity;
	private String addrState;
	private String addrZip;
}
