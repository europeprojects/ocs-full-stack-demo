package com.mak.demo.notification.manager.app.config;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncHttpClientConfig {
    @Bean
    public AsyncHttpClient httpClient() {
    	return Dsl.asyncHttpClient();
    }
}
