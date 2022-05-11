package com.mak.demo.poll.manager.app.config;

import com.mak.demo.poll.manager.app.auth.PollOAuth2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.realm}")
    private String clientRealm;
    @Value("${keycloak.check-token-uri}")
    private String checkTokenUri;
    @Value("${keycloak.client-secret}")
    private String clientSecret;


    public final String ignoredPaths[] = {"/actuator/**",  "/oauth2/**", "/v3/api-docs", "/configuration/**",
            "/swagger*/**", "/webjars/**","/h2-console/**","/h2-ui/**","/api/polls/sendNotificationEmail/**"};

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler() {
        return  new OAuth2WebSecurityExpressionHandler();
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().mvcMatchers(this.ignoredPaths).permitAll()
                .anyRequest().authenticated();
    }



    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl(checkTokenUri);
        tokenServices.setClientId(clientId);
        tokenServices.setClientSecret(clientSecret);
        tokenServices.setAccessTokenConverter(new KeycloakAccessTokenConverter());

        resources.tokenServices(tokenServices);
        resources.expressionHandler(oAuth2WebSecurityExpressionHandler());
        resources.resourceId(clientId);

    }

    public static class KeycloakAccessTokenConverter extends DefaultAccessTokenConverter {

        @Override
        public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
            OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) oAuth2Authentication.getOAuth2Request().getAuthorities();
            if (map.containsKey("authorities")) {
                List<String> clientAuths = (List<String>) map.get("authorities");
                if(Objects.nonNull(clientAuths)) {
                    clientAuths.forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));
                }
            }

            PollOAuth2 pollOAuth2 = new PollOAuth2(oAuth2Authentication.getOAuth2Request(),oAuth2Authentication.getUserAuthentication());
            if(map.containsKey("username") && map.containsKey("email")){
                pollOAuth2.setUsername((String) map.get("username"));
                pollOAuth2.setEmail((String) map.get("email"));
            }

            return pollOAuth2;
        }
    }


}
