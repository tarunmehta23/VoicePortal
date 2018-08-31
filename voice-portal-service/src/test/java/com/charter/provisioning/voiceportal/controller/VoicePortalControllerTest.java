package com.charter.provisioning.voiceportal.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.charter.provisioning.voiceportal.exception.ErrorResponse;
import com.charter.provisioning.voiceportal.exception.ServiceException;
import com.charter.provisioning.voiceportal.handler.VoicePortalHandler;
import com.charter.provisioning.voiceportal.model.VoicePortalResponse;
import com.charter.provisioning.voiceportal.util.MockObjectCreator;

@RunWith(MockitoJUnitRunner.class)
public class VoicePortalControllerTest {

	@InjectMocks
	private VoicePortalController voicePortalController;

	@Mock
	private VoicePortalHandler voicePortalHandler;

	@Test
	public void getVoicemailSettings_ValidTN_ExpectsHTTPOk() {
		when(voicePortalHandler.getVoicemailSettings(MockObjectCreator.VALID_TN, MockObjectCreator.EMPTY,
				MockObjectCreator.EMPTY, MockObjectCreator.TRANS_ID))
						.thenReturn(MockObjectCreator.validVoiceMailSettingsResponse());
		ResponseEntity<VoicePortalResponse> responseEntity = voicePortalController.getVoicemailSettings(
				MockObjectCreator.VALID_TN, MockObjectCreator.EMPTY, MockObjectCreator.EMPTY,
				MockObjectCreator.AUDIT_USER, MockObjectCreator.TRANS_ID);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void handleException_BadRequest_ExpectsServiceException() {
		String errorMessage = "Invalid telephone number";
		ServiceException ex = new ServiceException(HttpServletResponse.SC_BAD_REQUEST, errorMessage);

		ErrorResponse errorResponse = voicePortalController.handleException(MockObjectCreator.getHttpServletRequest(),
				MockObjectCreator.getHttpServletResponse(), ex);

		assertThat(errorResponse.getErrorMessage(), is(errorMessage));
	}

	@Test
	public void handleException_ClientError_ExpectsInternalServerException() {
		String errorMessage = "Internal server error";
		HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);

		ErrorResponse errorResponse = voicePortalController.handleException(MockObjectCreator.getHttpServletRequest(),
				MockObjectCreator.getHttpServletResponse(), ex);

		assertThat(errorResponse.getErrorMessage(),
				is(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR, errorMessage)));
	}

}
