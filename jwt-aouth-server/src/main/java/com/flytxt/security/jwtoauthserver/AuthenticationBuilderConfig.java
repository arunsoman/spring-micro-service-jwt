package com.flytxt.security.jwtoauthserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.flytxt.security.jwtoauthserver.authBuilder.AuthenticationType;

@Configuration
@EnableConfigurationProperties
public class AuthenticationBuilderConfig {
    @Value("${authentication.AuthenticationBuilder}")
    private String authenticationBuilder;
    
    @Bean
    @Primary
    public AuthenticationType getAuthenticationType() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
    		return (AuthenticationType) Class.forName(authenticationBuilder).newInstance();
    }
}
