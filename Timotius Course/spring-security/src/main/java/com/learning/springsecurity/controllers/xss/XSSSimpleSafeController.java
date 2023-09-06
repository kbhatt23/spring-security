package com.learning.springsecurity.controllers.xss;

import java.time.LocalTime;

import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.services.FileService;

@RestController
@RequestMapping("/xss/safe")
public class XSSSimpleSafeController {

	public static final String GOOD_NIGHT_GREET = "Good Night ";
	public static final String GOOD_MORNING_GREET = "Good Morning ";
	public static final LocalTime DAY_START_TIME = LocalTime.of(6, 0);
	public static final LocalTime DAY_END_TIME = LocalTime.of(18, 0);

	@Autowired
	private FileService fileService;

	@GetMapping(value = "/greet", produces = MediaType.TEXT_PLAIN_VALUE)
	public String greetMessage(@RequestParam(required = true) String name) {
		LocalTime now = LocalTime.now();
		String greetMessage = null;
		if (now.isAfter(DAY_START_TIME) && now.isBefore(DAY_END_TIME)) {
			greetMessage = GOOD_MORNING_GREET + name;
		} else {
			greetMessage = GOOD_NIGHT_GREET + name;
		}

		// if we send string directly it can execute script in browser if name contains
		// js script
		// but if we encode it using OWASP encoder it will take it as encoded string and
		// wont run in browser
		return Encode.forHtml(greetMessage);
	}

	// example of malicious site injecting script via file download
	@GetMapping(value = "/file")
	public ResponseEntity<Resource> downloadFile() {
		var resource = new ClassPathResource("static/fileWithXss.csv");
		// by default in spring boot since we are returning object of class it will be
		// application/json
		// but we must identify and for html type send as plain/text
		// so that browser do not run the script from this file

		MediaType fileMediaType = fileService.detectFileType(resource);

		return ResponseEntity.ok().contentType(fileMediaType).body(resource);
	}

	// example of good file
	@GetMapping(value = "/good-file")
	public ResponseEntity<Resource> downloadGoodFileFile() {
		var resource = new ClassPathResource("static/goodfile.txt");

		MediaType fileMediaType = fileService.detectFileType(resource);
		return ResponseEntity.ok().contentType(fileMediaType).body(resource);
	}
}
