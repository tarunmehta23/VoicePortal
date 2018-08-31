package com.charter.provisioning.voiceportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// TODO - Is this hard coding okay here ?
		httpSecurity.authorizeRequests().antMatchers("/voice-portal").permitAll().and().csrf().disable();
	}

}
