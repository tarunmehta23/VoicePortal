package com.charter.provisioning.voiceportal.model;

import lombok.Data;

@Data
public class SpcAccountDivisionList {

	// object model from spectrum core definition
	private String divisionID;
	private String soloAccountID;
	private String accountNumber;
	private String firstName;
	private String lastName;
	private String businessName;
	private String primaryPhone;
	private String secondaryPhone;
	private String otherPhone;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipCode5;
	private String zipCode4;
	private String accountStatus;
	private String sourceMSO;
	private String billingSystem;
	private String uCAN;

}
