package com.learning.springsecurity.controllers.xss;

import java.time.LocalTime;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xss/danger")
public class XSSSimpleDangerController {

	public static final String GOOD_NIGHT_GREET = "Good Night ";
	public static final String GOOD_MORNING_GREET = "Good Morning ";
	public static final LocalTime DAY_START_TIME = LocalTime.of(6, 0);
	public static final LocalTime DAY_END_TIME = LocalTime.of(18, 0);
	
	@GetMapping("/greet")
	public String greetMessage(@RequestParam(required = true) String name) {
		LocalTime now= LocalTime.now();
		
		if(now.isAfter(DAY_START_TIME) && now.isBefore(DAY_END_TIME)) {
			return GOOD_MORNING_GREET+name;
		}else {
			return GOOD_NIGHT_GREET+name;
		}
	}
	
	//example of malicious site injecting script via file download
	@GetMapping(value = "/file")
	public Resource downloadFile() {
		var resource = new ClassPathResource("static/fileWithXss.csv");
		return resource;
	}
	
	//example of good file
	@GetMapping(value = "/good-file")
	public Resource downloadGoodFileFile() {
		var resource = new ClassPathResource("static/goodfile.txt");
		return resource;
	}
}
