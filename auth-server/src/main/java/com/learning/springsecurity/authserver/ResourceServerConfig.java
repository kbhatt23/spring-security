package com.learning.springsecurity.authserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//This class is needed because in the same auth server we are exposing user_info api
//this shall check if acess_token(not auth otoekn as before itslef the acces toek should be recieved) is vlaid and is provided by client
@Configuration
@EnableResourceServer
public class ResourceServerConfig {

}
