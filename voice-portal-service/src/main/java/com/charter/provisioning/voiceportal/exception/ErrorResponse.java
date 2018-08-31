package com.charter.provisioning.voiceportal.exception;

import lombok.Data;

@Data
public class ErrorResponse {

	private String errorMessage;

	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
