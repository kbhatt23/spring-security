package com.learning.attackerwebsite.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AttackerController {

	@GetMapping("/hack-log-data")
	public void hackLogData(@RequestParam String cookie) {
		log.info("I got user's cookie using log injection!! {}", cookie);
	}

	@GetMapping("/hack-user-detail")
	public void hackUserDetail(@RequestParam String address, @RequestParam String creditCardNo) {
		log.info("I got user's detail using csv injection!! Address: {} - Credit Card No: {}", address, creditCardNo);
	}

}
