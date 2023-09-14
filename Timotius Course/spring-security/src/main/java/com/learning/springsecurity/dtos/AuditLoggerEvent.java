package com.learning.springsecurity.dtos;

import lombok.Data;

@Data
public class AuditLoggerEvent {

	private String timestamp;

	private String method;

	private String path;

	private String query;

	private String headers;

	private String requestBody;

	private int responseStatusCode;
}
