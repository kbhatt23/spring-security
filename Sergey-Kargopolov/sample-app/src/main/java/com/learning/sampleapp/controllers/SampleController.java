package com.learning.sampleapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class SampleController {

	@GetMapping("/attribute/{name}")
	public Object getInterceptorAttribute(@PathVariable String name, HttpServletRequest httpServletRequest) {
		
		return httpServletRequest.getAttribute(name);
	}
}
