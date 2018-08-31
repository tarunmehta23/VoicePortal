package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSUser {

	private String type;
	private String userId;
	private String emailDomain;
	private String isMaster;
	private String firstName;
	private String lastName;
	private String status;
	private String isDialEnabled;
	private String createDate;
	private String updateDate;
}
