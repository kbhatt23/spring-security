package com.learning.springsecurity.controllers.xss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.dtos.XssArticleSearchResponse;
import com.learning.springsecurity.entity.XSSArticleEntity;
import com.learning.springsecurity.repository.XSSArticleRepository;

@RestController
@RequestMapping("/xss/safe/article")
@CrossOrigin(origins = "http://localhost:3000")
public class XSSArticelSafeController {
	
	@Autowired
	private XSSArticleRepository repository;

	@PostMapping(produces = MediaType.TEXT_PLAIN_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveArticle(@RequestBody XSSArticleEntity xssArticleEntity) {
		XSSArticleEntity savedItem = repository.save(xssArticleEntity);
		return Long.toString(savedItem.getArticleId());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public XssArticleSearchResponse search(@RequestParam(required = true) String query) {
		var articles = repository.findByArticleContainsIgnoreCase(query);

		var response = new XssArticleSearchResponse();
		response.setResult(articles);

		if (articles.size() < 100) {
			response.setQueryCount(
					"Search for " + query + " returns <strong>" + articles.size() + "</strong> results.");
		} else {
			response.setQueryCount(
					"Search for " + query + " returns too many results. <em>Use more specific keyword.</em>");
		}

		return response;
	}
}
