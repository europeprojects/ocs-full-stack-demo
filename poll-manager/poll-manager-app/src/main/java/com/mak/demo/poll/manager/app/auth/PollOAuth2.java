package com.mak.demo.poll.manager.app.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

@Getter
@Setter
public class PollOAuth2 extends OAuth2Authentication {

    String username;
    String email;

    public PollOAuth2(OAuth2Request storedRequest, Authentication userAuthentication) {
        super(storedRequest, userAuthentication);
    }
}
