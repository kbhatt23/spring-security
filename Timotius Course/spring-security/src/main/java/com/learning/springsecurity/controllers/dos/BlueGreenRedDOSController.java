package com.learning.springsecurity.controllers.dos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/dos")
public class BlueGreenRedDOSController {

	private static final String GREEN = "green";
	private static final String BLUE = "blue";
	private static final String RED = "red";
	
	private static final Logger LOG = LoggerFactory.getLogger(BlueGreenRedDOSController.class);
	
	@GetMapping(BLUE)
	public String processBlue() {
		LOG.info("processBlue called");
		return BLUE;
	}
	
	@GetMapping(GREEN)
	public String processGreen() {
		LOG.info("processGreen called");
		return GREEN;
	}
	
	@GetMapping(RED)
	public String processRed() {
		LOG.info("processRed called");
		//mimicking some huge CPU utilization
		for(int i = Integer.MIN_VALUE ; i < Integer.MAX_VALUE ; i++) {
			//do nothing
		}
		
		return RED;
	}
	
}
