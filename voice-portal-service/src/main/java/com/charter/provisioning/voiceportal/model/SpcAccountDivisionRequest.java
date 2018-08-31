package com.charter.provisioning.voiceportal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName(value = "getSpcAccountDivisionRequest")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpcAccountDivisionRequest {

	private String systemID;
	private String accountNumber;
	private String telephoneNumber;
	private String lastName;
	private String firstName;
	private String businessName;
	private String streetNumber;
	private String streetName;
	private String apartment;
	private String city;
	private String state;
	private String zipCode5;
	private String emailAddress;
	private String uCAN;

}
