package com.charter.provisioning.voiceportal.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.charter.provisioning.voiceportal.exception.ServiceException;
import com.charter.provisioning.voiceportal.model.SpcAccountDivisionList;
import com.charter.provisioning.voiceportal.model.SpcAccountDivisionResponse;
import com.charter.provisioning.voiceportal.model.VoicePortalResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.twc.bps.swebapi.RequestResponse;
import com.twc.bps.webservice.AbstractObject;
import com.twc.bps.webservice.Data;
import com.twc.bps.webservice.WebapiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ResponseParser{
	
	private ResourceLoader resourceLoader;

	@Autowired
	public ResponseParser(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * BPS Response Parser
	 * 
	 * @param response, BPS Response
	 * @return List of Objects representing VMSettings
	 */
	public VoicePortalResponse parseBPSResponse(RequestResponse response, String transactionId) {

		WebapiResponse webapiResponse = response.getWebapiResponse();

		if (webapiResponse != null
				&& webapiResponse.getRespCode().equalsIgnoreCase(VoicePortalConstants.BPS_SUCCESS_CODE)) {

			/* In response there will be only one data object and only one account object */
			List<Data> dataList = webapiResponse.getData();

			if (CollectionUtils.isEmpty(dataList))
				throw new ServiceException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						String.format("Transaction id %s, BPS data is empty.", transactionId));

			List<AbstractObject> listAccountObjects = dataList.get(0).getObject();

			if (CollectionUtils.isEmpty(listAccountObjects))
				throw new ServiceException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						String.format("Transaction id %s, BPS Accounts info is empty.", transactionId));

			AbstractObject accountObject = listAccountObjects.get(0);

			if (accountObject == null)
				throw new ServiceException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						String.format("Transaction id %s, BPS Account info is empty.", transactionId));

			try {

				String xml = convertAbstractObjectToXML(accountObject);
				log.info("Transaction id {}, xml {}", transactionId, xml);

				String transformmedXML = transformXML(xml, transactionId);
				log.info("Transaction id {}, transformmedXML {}", transactionId, transformmedXML);

				VoicePortalResponse bpsAccountObject = convertXMLToVoicePortalResponse(transformmedXML);
				log.info("Transaction id {}, bpsAccountObject {}", transactionId, bpsAccountObject);

				return bpsAccountObject;

			} catch (TransformerException | IOException ex) {
				log.error("Transaction id {}, Parsing error {}", transactionId, ex.getMessage());

				throw new ServiceException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						String.format("Transaction id %s, Parsing error %s.", transactionId, ex.getMessage()));
			}

		}

		// We are handling this in VoicePortalHandler
		return null;

	}

	private String convertAbstractObjectToXML(AbstractObject accountObject) throws IOException {

		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.registerModule(new JaxbAnnotationModule());
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
		return xmlMapper.writeValueAsString(accountObject);
	}

	private String transformXML(String inputXML, String transactionId) throws TransformerException {

		StringReader inputStream = new StringReader(inputXML);
		StringWriter outputStream = new StringWriter();
		TransformerFactory factory = TransformerFactory.newInstance();
		Source xslt = null;
		
		try {
		
			Resource resource = resourceLoader.getResource("classpath:GCDResponseTransformation.xslt");
			
			log.info("Transaction id {}, GCDResponseTransformation.xslt getInputStream {}", transactionId, resource.getInputStream());
			
			xslt = new StreamSource(resource.getInputStream());
			
		} catch(IOException ex) {
			log.error("Transaction id {}, GCDResponseTransformation.xslt file loading  exception {}", transactionId, ex.getMessage());
			throw new ServiceException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					String.format("Transaction id %s, GCDResponseTransformation.xslt file loading exception %s", transactionId, ex.getMessage()));
		}

		Templates template = factory.newTemplates(xslt);
		Transformer transformer = template.newTransformer();
		transformer.transform(new StreamSource(inputStream), new StreamResult(outputStream));
		return outputStream.toString();
	}

	private VoicePortalResponse convertXMLToVoicePortalResponse(String xml) throws IOException {

		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return xmlMapper.readValue(xml, VoicePortalResponse.class);
	}

	
	/**
	 * SpringCore Response Parser
	 * 
	 * @param response, Spring Core Response
	 * @return Set of unique siteIds
	 */
	public HashSet<String> getSiteId(ResponseEntity<SpcAccountDivisionResponse> response) {

		HashSet<String> uniqueDivisions = new HashSet<String>();

		SpcAccountDivisionResponse res = response.getBody();
		ArrayList<SpcAccountDivisionList> accountDivisionList = (ArrayList<SpcAccountDivisionList>) res
				.getSpcAccountDivisionList();

		if (!CollectionUtils.isEmpty(accountDivisionList)) {
			for (SpcAccountDivisionList accountMap : accountDivisionList) {
				if (accountMap.getAccountStatus().equalsIgnoreCase(VoicePortalConstants.ACTIVE)) {
					// all characters before the period in the division id is the site id
					uniqueDivisions
							.add((accountMap.getDivisionID()).substring(0, accountMap.getDivisionID().indexOf(".")));
				}
			}
		}
		return uniqueDivisions;
	}

}
