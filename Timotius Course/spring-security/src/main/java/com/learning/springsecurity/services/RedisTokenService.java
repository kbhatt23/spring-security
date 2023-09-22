package com.learning.springsecurity.services;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.springsecurity.dtos.RedisToken;
import com.learning.springsecurity.util.SaltGenerator;

import io.lettuce.core.api.StatefulRedisConnection;

@Service
public class RedisTokenService {

	@Autowired
	private StatefulRedisConnection<String, String> connection;

	@Autowired
	private ObjectMapper objectMapper;

	public String store(RedisToken redisToken) {
		String tokenId = SaltGenerator.generateSaltHex();
		try {
			connection.sync().setex(tokenId, 120, objectMapper.writeValueAsString(redisToken));
			return tokenId;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return StringUtils.EMPTY;
	}

	public Optional<RedisToken> read(String tokenId) {
		if (StringUtils.isBlank(tokenId))
			return Optional.empty();

		RedisToken redisToken = null;
		try {
			String tokenDataStr = connection.sync().get(tokenId);
			if(tokenDataStr == null)
				return Optional.empty();
			redisToken = objectMapper.readValue(tokenDataStr, RedisToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(redisToken);
	}

	public void delete(String tokenId) {
		if (StringUtils.isBlank(tokenId))
			return;

		connection.sync().del(tokenId);

	}
}
