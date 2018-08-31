package com.charter.provisioning.voiceportal.util;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twc.bps.swebapi.ObjectFactory;
import com.twc.bps.swebapi.Request;
import com.twc.bps.webservice.Arguments;
import com.twc.bps.webservice.Property;
import com.twc.bps.webservice.WebapiRequest;

@Component
public class BPSRequestBuilder {

	@Value("${bps.clientId}")
	private String clientId;

	/**
	 * Generic BPS request builder
	 * 
	 * @param inputs,
	 *            Contains request attributes to BPS.
	 * @param action,
	 *            Contains action needs to be called.
	 * @return Request built using the above input params
	 */
	public Request buildBPSRequest(Map<String, String> inputs, String action) {

		ObjectFactory factory = new ObjectFactory();
		Request request = factory.createRequest();

		WebapiRequest webApiRequest = new WebapiRequest();

		Arguments args = new Arguments();
		Property prop;

		for (Map.Entry<String, String> entry : inputs.entrySet()) {
			prop = new Property();
			prop.setName(entry.getKey());
			prop.setValue(entry.getValue());
			args.getArg().add(prop);
		}

		webApiRequest.setArguments(args);
		webApiRequest.setTransId((UUID.randomUUID()).toString());
		webApiRequest.setClientId(clientId);
		webApiRequest.setAction(action);

		request.setWebapiRequest(webApiRequest);

		return request;

	}

}
