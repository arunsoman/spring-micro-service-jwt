package com.flytxt.security.jwtoauthserver;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Configuration
@EnableConfigurationProperties
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	private static final String LDAP_KEY ="LDAP"; 
    
	/** for checking the authentication.Possible values are   
	 * 	
	 *  1.  LDAP -  For LDAP validation .
	 *  2.  DB - USer details from DB  
	 *  
	 *  **/
	@Value("${authentication.mode}")
    private String authenticationMode;
	
	
	/** LDAP Directory Domain  eg. flytxt.com*/
	@Value("${authentication.mode.ldap.activeDirectoryDomain}")
    private String activeDirectoryDomain;
	
	/** LDAP Directory URL eg :  ldap://serverIP:389 */
	@Value("${authentication.mode.ldap.activeDirectoryUrl}")
    private String activeDirectoryUrl;
	
	
	@Autowired
	DataSource dataSource;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling()
				.authenticationEntryPoint(
						(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and().authorizeRequests().antMatchers("/**").authenticated().and().httpBasic();
		/*
		http.formLogin().loginPage("/login").permitAll().and()
				.requestMatchers().antMatchers( "/oauth/authorize", "/oauth/confirm_access","/oauth/token",
						"/oauth/check_token","/oauth/token_key","/console")
				.and()
				.authorizeRequests().anyRequest().authenticated();*/
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		if(LDAP_KEY.equalsIgnoreCase(authenticationMode)){
			
			ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(
					activeDirectoryDomain, activeDirectoryUrl);
			provider.setConvertSubErrorCodesToExceptions(true);
		    provider.setUseAuthenticationRequestCredentials(true);		     
			auth.authenticationProvider(provider);
			//auth.parentAuthenticationManager(authenticationManagerBean());
		}else{					
			auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select username,password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
		}		
		
	}
}
