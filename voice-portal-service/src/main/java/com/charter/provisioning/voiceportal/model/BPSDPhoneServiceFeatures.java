package com.charter.provisioning.voiceportal.model;

import java.util.List;

import com.charter.provisioning.voiceportal.util.VoicePortalConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_DEFAULT)
public class BPSDPhoneServiceFeatures {

	@JsonProperty("package")
	@JacksonXmlProperty(localName = "package")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<String> bpsDPhoneFeaturesPackages;
	
	//Case issues
	@JacksonXmlProperty(localName = "Privacy")
	private String Privacy;
	
	private String voicemailAccessNumber;
	
	private String voicemailFQDN;
	
	@JsonProperty("SCA")
	@JacksonXmlProperty(localName = "SCA")
	private BPSDPhoneServiceFeaturesSCA bpsDPhoneServiceFeaturesSCA;
	
	@JsonProperty("SCR")
	@JacksonXmlProperty(localName = "SCR")
	private BPSDPhoneServiceFeaturesSCR bpsDPhoneServiceFeaturesSCR;
	
	@JsonProperty("SIMULRING")
	@JacksonXmlProperty(localName = "SIMULRING")
	private BPSDPhoneServiceFeaturesSIMULRING bpsDPhoneServiceFeaturesSIMULRING;
	
	@JsonProperty("SCF")
	@JacksonXmlProperty(localName = "SCF")
	private BPSDPhoneServiceFeaturesSCF bpsDPhoneServiceFeaturesSCF;
	
	@JacksonXmlProperty(localName = "STB")
	private String STB = VoicePortalConstants.DEFAULT;
	
	@JsonProperty("3WAY")
	@JacksonXmlProperty(localName = "_3WAY")
	private String _3WAY = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "CT")
	private String CT = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "CWAIT")
	private String CWAIT = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "CWCID")
	private String CWCID = VoicePortalConstants.DEFAULT;
	
	@JsonProperty("CFU")
	@JacksonXmlProperty(localName = "CFU")
	private BPSDPhoneServiceFeaturesCFU CFU;
	
	@JsonProperty("SC1D")
	@JacksonXmlProperty(localName = "SC1D")
	private BPSDPhoneServiceFeaturesSC1D bpsDPhoneServiceFeaturesSC1D;
	
	@JsonProperty("ACR")
	@JacksonXmlProperty(localName = "ACR")
	private BPSDPhoneServiceFeaturesACR bpsDPhoneServiceFeaturesACR;
	
	@JsonProperty("CFB")
	@JacksonXmlProperty(localName = "CFB")
	private BPSDPhoneServiceFeaturesCFB bpsDPhoneServiceFeaturesCFB;
	
	@JsonProperty("CFNA")
	@JacksonXmlProperty(localName = "CFNA")
	private BPSDPhoneServiceFeaturesCFNA bpsDPhoneServiceFeaturesCFNA;
	
	@JacksonXmlProperty(localName = "ACC")
	private String ACC = VoicePortalConstants.DEFAULT;
	
	@JsonProperty("GSC2D")
	@JacksonXmlProperty(localName = "GSC2D")
	private BPSDPhoneServiceFeaturesGSC2D bpsDPhoneServiceFeaturesGSC2D;
	
	@JacksonXmlProperty(localName = "CPRKR")
	private String CPRKR = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "DPU")
	private String DPU = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "CH")
	private String CH = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "DACW")
	private String DACW = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "CDP")
	private String CDP = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "DRING")
	private String DRING = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "MOBILITY")
	private String MOBILITY = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "DREF")
	private String DREF = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "IBR")
	private String IBR = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "OBR")
	private String OBR = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "HOTLINE")
	private String HOTLINE = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "SUSPEND")
	private String SUSPEND = VoicePortalConstants.DEFAULT;
	
	@JsonProperty("BLOCK")
	@JacksonXmlProperty(localName = "BLOCK")
	private BPSDPhoneServiceFeaturesBLOCK bpsDPhoneServiceFeaturesBLOCK;
	
	@JacksonXmlProperty(localName = "CNAM")
	private String CNAM = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "CNAMB")
	private String CNAMB = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "ICNAM")
	private String ICNAM = VoicePortalConstants.DEFAULT;
	
	@JacksonXmlProperty(localName = "BC01")
	private String BC01 = VoicePortalConstants.DEFAULT;

	@JsonProperty("CFD")
	@JacksonXmlProperty(localName = "CFD")
	private BPSDPhoneServiceFeaturesCFD bpsDPhoneServiceFeaturesCFD;
	
	@JsonProperty("VMAIL")
	@JacksonXmlProperty(localName = "VMAIL")
	private BPSDPhoneServiceFeaturesVMAIL bpsDPhoneServiceFeaturesVMAIL;
	
	@JsonProperty("HGROUP")
	@JacksonXmlProperty(localName = "HGROUP")
	private BPSDPhoneServiceFeaturesHGroup bpsDPhoneServiceFeaturesHGroup;
	
	@JsonProperty("BGROUP")
	@JacksonXmlProperty(localName = "BGROUP")
	private BPSDPhoneServiceFeaturesBGroup bpsDPhoneServiceFeaturesBGroup;
	
	@JsonProperty("AGROUP")
	@JacksonXmlProperty(localName = "AGROUP")
	private BPSDPhoneServiceFeaturesAGroup bpsDPhoneServiceFeaturesAGroup;
}
