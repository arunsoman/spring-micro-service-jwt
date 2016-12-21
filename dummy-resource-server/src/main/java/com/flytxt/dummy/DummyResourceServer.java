package com.flytxt.dummy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.flytxt.security.rsc.JwtConfig;
import com.flytxt.security.rsc.ResourceServerConfig;

@SpringBootApplication
@Import({ResourceServerConfig.class,JwtConfig.class})
public class DummyResourceServer {

	public static void main(String[] args) {		
		
		SpringApplication.run(DummyResourceServer.class, args);
	}
}
