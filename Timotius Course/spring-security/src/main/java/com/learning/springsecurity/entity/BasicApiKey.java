package com.learning.springsecurity.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "basic_apikey")
@Data
public class BasicApiKey {

	@Id
	@Column(name = "apikey_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private int userId;
	
	@Column(unique = true, name = "apikey")
	private String apiKey;
	
	private LocalDateTime expiredAt;
}
