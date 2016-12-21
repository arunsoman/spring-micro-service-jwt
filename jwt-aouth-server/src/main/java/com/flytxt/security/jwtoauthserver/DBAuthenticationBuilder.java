package com.flytxt.security.jwtoauthserver;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/**
 * 
 * @author shiju.john
 *
 */
@Profile("db")
@Configuration
public class DBAuthenticationBuilder implements AuthenticationType{

	
	@Autowired
	DataSource dataSource;
	
	

	@Override
	public void setAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username,password, enabled from users where username=?")
		.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
		
	}

}
