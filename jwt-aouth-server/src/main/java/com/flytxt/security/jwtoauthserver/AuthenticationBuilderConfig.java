package com.flytxt.security.jwtoauthserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.flytxt.security.jwtoauthserver.authBuilder.AuthenticationType;

public class AuthenticationBuilderConfig {
    @Value("${authentication.AuthenticationBuilder}")
    private String authenticationBuilder;
    
    @Bean
    public AuthenticationType getAuthenticationType() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
    		return (AuthenticationType) Class.forName(authenticationBuilder).newInstance();
    }
}
