package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BPSVMailService {

	private String type;
	private String phoneNumber;
	private String category;
	private String mode;
	private String role;
	
	//Case issues
	@JacksonXmlProperty(localName = "COI")
	private String COI;
	
	private String gender;
	private String cn;
	private String givenname;
	private String sn;
	private String timezone;
	private String billingnumber;
	private String ummwiswitch;
	
	private String msghost;
	private String mailhost;
	private String notifyhost;
	private String coverphonenumber;
	private String mail;
	private String mailforwardingaddress;
	private String telephonenumber;
	private String groupmailboxenabled;
	
	//issue property name as 'package'
	@JsonProperty("package")
	@JacksonXmlProperty(localName = "package")
	private String _package;
	
}
