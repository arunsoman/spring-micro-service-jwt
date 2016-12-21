package com.flytxt.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.flytxt.security.rsc.JwtConfig;

@Configuration
@EnableResourceServer
@Import(JwtConfig.class)
@EnableConfigurationProperties
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
    @Autowired
    TokenStore tokenStore;
    
    @Autowired
    DefaultTokenServices tokenServices;

    @Value("${resource.id}")
    private String id;
    
	@Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.resourceId(id).tokenStore(tokenStore);
        config.resourceId(id).tokenServices(tokenServices);
	}
	
	    
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeRequests()        
        .and().authorizeRequests().antMatchers("/**").authenticated();
        
        /*.antMatchers("/**").authenticated()
        .antMatchers(HttpMethod.GET, "/foo").hasAuthority("FOO_READ")
        .antMatchers(HttpMethod.POST, "/foo").hasAuthority("FOO_WRITE");*/

		
    }
	
}
