package com.learning.springsecurity.services;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class FileService {
	
	private Tika tika = new Tika();

	public MediaType detectFileType(Resource resource) {
		String fileType = null;
		try {
			fileType = tika.detect(resource.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isBlank(fileType) || StringUtils.equalsIgnoreCase(fileType, MediaType.TEXT_HTML_VALUE)) {
			fileType = MediaType.TEXT_PLAIN_VALUE;
		}
		return MediaType.parseMediaType(fileType);
	}
}
