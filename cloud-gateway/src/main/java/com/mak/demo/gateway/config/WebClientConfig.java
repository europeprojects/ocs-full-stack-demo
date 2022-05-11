package com.mak.demo.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConditionalOnBean(value = SecurityConfig.class)
public class WebClientConfig {

	@Value("${keycloak.client-id}")
	private String clientId;


	@Bean
	public ServerOAuth2AuthorizedClientExchangeFilterFunction filter(ReactiveClientRegistrationRepository clientRegistrationRepository,
			ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {
		ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
				clientRegistrationRepository, authorizedClientRepository);
		oauth.setDefaultOAuth2AuthorizedClient(true);
		oauth.setDefaultClientRegistrationId(clientId);
		return oauth;
	}

	// non-blocking, reactive alternative for RestTemplate

	@Bean
	@LoadBalanced
	public WebClient webClient(ServerOAuth2AuthorizedClientExchangeFilterFunction oauth) {
		return WebClient.builder()
				.filter(oauth).build();
	}


}
