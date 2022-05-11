package com.mak.demo.gateway.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeycloakTokenResponse {

    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private Integer refresh_expires_in;
    private String id_token;

}
