package com.charter.provisioning.voiceportal.model;

import java.util.List;

import lombok.Data;

@Data
public class BPSInstance {

	private List<String> siteIds;
	private String webApiUrl;
	private String user;
	private String password;
}
