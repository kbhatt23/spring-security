package com.learning.spring_security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration

//TODO-9 uncomment the below annotation to enable all filters 
//required for a resource server
//this is needed to add filters for userinfo controller

@EnableResourceServer
public class MyResourceServerConfiguration{

}
