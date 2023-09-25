package com.learning.springsecurity.controllers.apikey;

import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.constants.BasicApiAuthConstants;

@RestController
@RequestMapping("/auth/apikey")
public class ApiKeyController {

	// we need to secure/restrict this api only using api key
	@GetMapping(value = "/time", produces = MediaType.TEXT_PLAIN_VALUE)
	public String time(HttpServletRequest request) {
		return "Current time is: " + LocalTime.now() + ", accessed by: "
				+ request.getAttribute(BasicApiAuthConstants.REQUEST_ATTRIBUTE_USERNAME);
	}

}
