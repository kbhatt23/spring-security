package com.learning.springsecurity.helloworld.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@Controller
public class HelloGoogleAouth2Controller {
	
	//@Autowired
	//private OAuth2RestTemplate oAuth2RestTemplate;
	
	@GetMapping("/hello")
	public String sayHello(Model model, @RequestParam(defaultValue = "SitaRam" ,required = false)String name) throws Exception, URISyntaxException {
		
		model.addAttribute("name", name);
		
		//return oAuth2RestTemplate.getForObject(new URI("http://localhost:8084/hello"), String.class);
		return "hello";
	}
	
	
	@GetMapping("/home")
	public String home() {
		return "hello";
	}

}
