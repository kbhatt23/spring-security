package com.learning.springsecurity.controllers.dos;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.dtos.HexColor;
import com.learning.springsecurity.dtos.HexColorPaginationResponse;

@RestController
@RequestMapping("/dos")
public class HexColorDOSController {
	
	private static final int COLOR_SIZE = 1000000;
	
	private List<HexColor> HEX_COLORS;
	
	private static final Logger LOG = LoggerFactory.getLogger(HexColorDOSController.class);
	
	private static String generateRandomColor() {
		// create a random number between 0 and ffffff (hex)
		var randomInt = ThreadLocalRandom.current().nextInt(0xffffff + 1);

		// format as hashtag and leading zeros (hex color code)
		return String.format("#%06x", randomInt);
	}
	public HexColorDOSController() {
		LOG.info("creating hex colors.");
		HEX_COLORS = IntStream.rangeClosed(1, COLOR_SIZE)
		         .parallel()
		         .mapToObj(number -> new HexColor(number, generateRandomColor()))
		         .collect(Collectors.toList());
		
		LOG.info("all hex colors created.");
	}
	
	@GetMapping("/hex-colors")
	public List<HexColor> findAllHexColors(){
		return HEX_COLORS;
	}
	
	@GetMapping("/hex-colors/paginate")
	public HexColorPaginationResponse findAllHexColorsPaginate(@RequestParam(required = true) int size 
			, @RequestParam(required = true) int page){
		 List<HexColor> hexColors = HEX_COLORS.stream()
                   .skip(page * size)
                   .limit(size)
                   .collect(Collectors.toList());
		 
		 return new HexColorPaginationResponse(hexColors, size, page, (HEX_COLORS.size() / size) - 1);
	}

	
}
