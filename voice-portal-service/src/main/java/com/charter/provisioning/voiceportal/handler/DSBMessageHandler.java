package com.charter.provisioning.voiceportal.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.charter.provisioning.dsb.model.ASBMessage;
import com.charter.provisioning.dsb.model.ASBMessageConst;
import com.charter.provisioning.dsb.model.Account;
import com.charter.provisioning.dsb.model.Customer;
import com.charter.provisioning.dsb.util.ASBMessageHandler;
import com.charter.provisioning.voiceportal.exception.ServiceException;
import com.charter.provisioning.voiceportal.http.HttpTransportHandler;
import com.charter.provisioning.voiceportal.util.VoicePortalConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DSBMessageHandler {

	private HttpTransportHandler httpTransportHandler;

	@Autowired
	public DSBMessageHandler(HttpTransportHandler httpTransportHandler) {
		this.httpTransportHandler = httpTransportHandler;
	}

	/**
	 * Queries DSB by customerGuid.
	 * 
	 * @param transactionId TransactionId for this request.
	 * @param customerGuid customerGuid to get account info
	 * @return Map consists site-id, account-number
	 */
	protected Map<String, String> queryByCustomerGuid(String transactionId, String customerGuid) {

		log.info("Transaction id {}, CustomerGuid {}", transactionId, customerGuid);

		ASBMessageHandler asbMessageHandler = new ASBMessageHandler();
		String asbRequest = asbMessageHandler.buildCustomerGuidRequest(transactionId, customerGuid);
		log.info("Transaction id {}, asbRequest {}", transactionId, asbRequest);

		String response = httpTransportHandler.sendAndReceive(transactionId, asbRequest);
		log.info("Transaction id {}, response {}", transactionId, response);

		ASBMessage asbResponse = asbMessageHandler.buildASBMessageResponse(transactionId, response);
		log.info("Transaction id {}, asbResponse {}", transactionId, asbResponse.toString());

		if (CollectionUtils.isEmpty(asbResponse.getBody().getCustomer())) {
			log.error("Transaction id {}, DSB missing customer info {}", transactionId, asbResponse.toString());
			throw new ServiceException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
					String.format("Transaction id %s, DSB missing customer info %s.", transactionId, asbResponse.toString()));
		}

		// Got the confirmation there will be only one customer all the time..
		Customer customer = asbResponse.getBody().getCustomer().get(0);

		return buildAccountInfo(transactionId, customer.getAccount());
	}

	/**
	 * Queries DSB by accountGuid.
	 * 
	 * @param transactionId TransactionId for this request.
	 * @param accountGuid accountGuid to get account info
	 * @return List consists site-id, account-number
	 */
	protected Map<String, String> queryByAccountGuid(String transactionId, String accountGuid) {

		log.info("Transaction id {}, AccountGuid {}", transactionId, accountGuid);

		ASBMessageHandler asbMessageHandler = new ASBMessageHandler();
		String asbRequest = asbMessageHandler.buildAccountGuidRequest(transactionId, accountGuid);
		log.info("Transaction id {}, asbRequest {}", transactionId, asbRequest);

		String response = httpTransportHandler.sendAndReceive(transactionId, asbRequest);
		log.info("Transaction id {}, response {}", transactionId, response);

		ASBMessage asbResponse = asbMessageHandler.buildASBMessageResponse(transactionId, response);
		log.info("Transaction id {}, asbResponse {}", transactionId, asbResponse.toString());

		return buildAccountInfo(transactionId, asbResponse.getBody().getAccount());
	}

	private Map<String, String> buildAccountInfo(String transactionId, Account account) {

		if (account == null || account.getDivision() == null || account.getDivision().getId() == null) {
			log.error("Transaction id {}, DSB missing account info {}", transactionId, account);
			// We are handling this in Handler.
			return new HashMap<>();
		}

		Map<String, String> accountInfo = new HashMap<>();

		accountInfo.put(VoicePortalConstants.SITE_CODE, account.getDivision().getId());

		account.getID().stream().filter(p -> p.getNs().equals(ASBMessageConst.ASB_ID_VALUE_ESB)).findFirst()
				.ifPresent(id1 -> accountInfo.put(VoicePortalConstants.ACCOUNT_NUMBER, id1.getValue()));

		log.info("Transaction id {}, accountInfo {}", transactionId, accountInfo.toString());

		return accountInfo;
	}

}
