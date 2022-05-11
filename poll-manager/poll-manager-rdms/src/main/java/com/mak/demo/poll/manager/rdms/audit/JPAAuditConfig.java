package com.mak.demo.poll.manager.rdms.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JPAAuditConfig {

	private final static String DEMO_USER = "demoUser";
	
	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> {
			return Optional.ofNullable(DEMO_USER);
		};
	}
	
}
