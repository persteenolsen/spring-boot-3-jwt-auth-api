package com.spring3.oauth.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class OauthJwtApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OauthJwtApplication.class, args);
    }

	// Ready for builing a war file used for Tomcat at Azure
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OauthJwtApplication.class);
	}
}
