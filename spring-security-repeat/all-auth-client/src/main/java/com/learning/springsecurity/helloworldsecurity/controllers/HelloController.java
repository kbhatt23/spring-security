package com.learning.springsecurity.helloworldsecurity.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class HelloController {
	@Autowired
	private WebClient webClient;
	

	@GetMapping("/hello")
	public String returnLogin() {
		//adding the below code for sample resource server call
		String message;
		callReourceAPI();
		
				 
		// dispatches to hello.html view
		return "hello";
	}

	private void callReourceAPI() {
		String message;
		try {
			message = webClient.get()
					 .uri(new URI("http://localhost:9999/resource"))
					 .retrieve()
					 .bodyToMono(String.class)
					 .block();
			System.out.println("ram ram jai raj ram says the message "+message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
