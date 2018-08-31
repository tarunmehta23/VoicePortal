package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSDevice {

	private String serviceCodes;
	private String serviceProvider;
	private String macAddress;
	private String isDocsis;
	private String type;
	private String status;
	private String quarantineStatus;
	private String createDate;
	private String updateDate;
	
	//Following only for MTA devices
	private String prefix;
	private String profile;
	private String maxlines;
	private String vendor;
	private String model;
	private String serialNumber;
	
}
