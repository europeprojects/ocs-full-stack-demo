package com.mak.demo.gateway.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {
    Boolean success;
    String accessToken;
    PollUser user;
}
