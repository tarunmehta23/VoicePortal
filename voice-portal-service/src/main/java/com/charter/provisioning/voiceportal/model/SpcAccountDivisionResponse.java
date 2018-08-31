package com.charter.provisioning.voiceportal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@Data
@JsonTypeName(value = "getSpcAccountDivisionResponse")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpcAccountDivisionResponse {

	private String uCan;
	private List<SpcAccountDivisionList> spcAccountDivisionList;
	private String systemId;
	private String searchLimitReached;
}
