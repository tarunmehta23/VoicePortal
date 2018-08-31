package com.charter.provisioning.voiceportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.charter.health.HealthStatServlet;
import com.charter.health.HealthStatServletBuilder;

@SpringBootApplication
public class VoicePortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoicePortalApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean healthServlet(ApplicationContext applicationContext) {
		HealthStatServlet servlet = HealthStatServletBuilder.create("voice-portal")
				.withManifestDetails(VoicePortalApplication.class).build();
		return new ServletRegistrationBean(servlet, "/health");
	}

}
