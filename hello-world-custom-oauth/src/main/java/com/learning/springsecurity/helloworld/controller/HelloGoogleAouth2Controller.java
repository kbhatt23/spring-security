package com.learning.springsecurity.helloworld.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class HelloGoogleAouth2Controller {
	
	@Autowired
	private WebClient webClient;
	
	@GetMapping("/hello")
	public String sayHello(Model model, @RequestParam(defaultValue = "SitaRam" ,required = false)String name) throws Exception, URISyntaxException {
		
		model.addAttribute("name", name);
		//calling resource server, token is auto populated form security context
		String response=webClient.get().uri(new URI("http://localhost:9091/resource")).retrieve().bodyToMono(String.class).block();
		System.out.println("message from resource server "+response);
		return "hello";
	}
	
	
	@GetMapping("/home")
	public String home() {
		return "hello";
	}

}
