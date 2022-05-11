package com.mak.demo.gateway.controller;

import com.mak.demo.gateway.model.KeycloakTokenResponse;
import com.mak.demo.gateway.model.LoginRequest;
import com.mak.demo.gateway.model.LoginResponse;
import com.mak.demo.gateway.model.PollUser;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/oauth2")
public class Controller {

    @GetMapping("/")
    public String index(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails data = (UserDetails) auth.getPrincipal();
        return principal.getName();
    }

    //Constants
    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.client-id}")
    private String keycloakClientId;

    @Value("${keycloak.client-secret}")
    private String keycloakClientSecret;

    @Value("${keycloak.token-uri}")
    private String keycloakTokenUri;



    @PostMapping("/token")
    public ResponseEntity<LoginResponse> getNewToken(@RequestBody LoginRequest loginRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", "password");
            map.add("client_id", keycloakClientId);
            map.add("client_secret", keycloakClientSecret);
            map.add("username", loginRequest.getUsernameOrEmail());
            map.add("password", loginRequest.getPassword());
            //map.add("scope", "openid");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<KeycloakTokenResponse> response = restTemplate
                    .postForEntity(keycloakTokenUri, request, KeycloakTokenResponse.class);
            KeycloakTokenResponse keycloakTokenResponse = response.getBody();
            LoginResponse loginResponse = LoginResponse.builder().accessToken(keycloakTokenResponse.getAccess_token()).user(null).build();

            return ResponseEntity.ok().body(loginResponse);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new RuntimeException("AUTHENTICATION_FAILED");
            } else {
                throw new RuntimeException(exception);
            }
        }
    }
}