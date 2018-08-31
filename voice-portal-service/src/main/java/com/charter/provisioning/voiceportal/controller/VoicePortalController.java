package com.charter.provisioning.voiceportal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charter.provisioning.voiceportal.exception.ErrorResponse;
import com.charter.provisioning.voiceportal.exception.ServiceException;
import com.charter.provisioning.voiceportal.handler.VoicePortalHandler;
import com.charter.provisioning.voiceportal.model.VoicePortalResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(value = "Voicemail Settings Service")
@RequestMapping(value = "/voicemail-settings")
public class VoicePortalController {

	private VoicePortalHandler voicePortalHandler;

	@Autowired
	public VoicePortalController(VoicePortalHandler voicePortalHandler) {
		this.voicePortalHandler = voicePortalHandler;
	}

	@ApiOperation(value = "Endpoint for retrieving Voicemail Settings by Telephone Number, Account GUID or Customer GUID.")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = VoicePortalResponse.class, responseContainer = "Return Object"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error") })
	@GetMapping(produces = "application/json")
	public ResponseEntity<VoicePortalResponse> getVoicemailSettings(
			@RequestParam(value = "telephone-number", required = false) String telephoneNumber,
			@RequestParam(value = "account-guid", required = false) String accountGUID,
			@RequestParam(value = "customer-guid", required = false) String customerGUID,
			@RequestHeader(value = "audit-user", required = false) String auditUser,
			@RequestHeader(value = "transaction-id", required = false) String transactionId) {

		log.info(
				"Transaction id {}, Retrieving Voicemail settings request, Telephone Number: {}, Account GUID:{}, Customer GUID: {}, Audit user {}",
				transactionId, telephoneNumber, accountGUID, customerGUID, auditUser);

		VoicePortalResponse response = voicePortalHandler.getVoicemailSettings(telephoneNumber, accountGUID, customerGUID,
				transactionId);

		log.info("Transaction id {}, Voicemail settings response {}", transactionId, response.toString());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler
	public ErrorResponse handleException(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		if (ex instanceof ServiceException) {
			response.setStatus(((ServiceException) ex).getHttpStatus());
		}

		log.error("Transaction id {}, Voicemail settings exception response {}", request.getHeader("transaction-id"),
				ex.toString());

		return new ErrorResponse(ex.getMessage());
	}

}
