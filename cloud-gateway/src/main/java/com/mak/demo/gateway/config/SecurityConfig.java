package com.mak.demo.gateway.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
    
    public final String ignoredPaths[] = {"/actuator/**",  "/oauth2/**", "/v3/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**"};

    @Value("${keycloak.realm}")
    private String issuer;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.httpBasic().disable()
            .csrf().disable()
            .cors()
            .and()
            .authorizeExchange()
            .pathMatchers(this.ignoredPaths).permitAll()
            .anyExchange().authenticated()
            .and()
            .oauth2Client().and().oauth2ResourceServer().jwt()
            .and()
            .authenticationEntryPoint((exchange, exception) -> Mono.error(new RuntimeException("INVALID_ACCESS_TOKEN")))
            .accessDeniedHandler(((exchange, exception) -> Mono.error(new RuntimeException("FORBIDDEN"))))
            .and()
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setMaxAge(31536000L); // 1 year
        configuration.setAllowCredentials(true);

        List<String> exposedHeaders = new ArrayList<>();
        exposedHeaders.add("Content-Disposition");
        configuration.setExposedHeaders(exposedHeaders);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
                JwtDecoders.fromOidcIssuerLocation(issuer);

        return jwtDecoder;
    }

}
