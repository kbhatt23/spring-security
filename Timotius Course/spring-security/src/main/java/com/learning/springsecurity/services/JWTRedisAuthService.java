package com.learning.springsecurity.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.learning.springsecurity.constants.JWTAuthConstants;
import com.learning.springsecurity.dtos.JWTRedisDataToken;
import com.learning.springsecurity.util.SaltGenerator;

import io.lettuce.core.api.StatefulRedisConnection;

@Service
public class JWTRedisAuthService {
	
	@Autowired
	private StatefulRedisConnection<String, String> connection;
	
	public String store(JWTRedisDataToken jwtDataToken) {
		var algo = Algorithm.HMAC256(JWTAuthConstants.JWT_HMAC_SECRET);
		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
		LocalDateTime expiry = now.plusMinutes(JWTAuthConstants.JWT_EXPIRY_MINUTES);
		Date currentDate = Date.from(now.toInstant(ZoneOffset.UTC));
		
		String jwtId = SaltGenerator.generateSaltHex(32);
		connection.sync().setex(jwtId, JWTAuthConstants.JWT_EXPIRY_MINUTES * 60,jwtDataToken.getUserName());//no need to store anything much
		
		return JWT.create()
		   .withSubject(jwtDataToken.getUserName())
		   .withIssuer(JWTAuthConstants.JWT_ISSUER)
		   .withAudience(JWTAuthConstants.JWT_AUDIENCE)
		   .withExpiresAt(Date.from(expiry.toInstant(ZoneOffset.UTC)))
		   .withClaim(JWTAuthConstants.JWT_PRIVATE_CLAIM_NAME, JWTAuthConstants.JWT_PRIVATE_CLAIM_VALUE)
		   .withClaim(JWTAuthConstants.JWT_OTHER_ATTRIBUTE_CLAIM, jwtDataToken.getOtherField())
		   .withIssuedAt(currentDate)
		   .withNotBefore(currentDate)
		   .withJWTId(jwtId)
		   .sign(algo);
	}
	
	public Optional<JWTRedisDataToken> read(String jwtToken){
		try {
		var algo = Algorithm.HMAC256(JWTAuthConstants.JWT_HMAC_SECRET);
		
		JWTVerifier jwtVerifier = JWT.require(algo)
		   .withIssuer(JWTAuthConstants.JWT_ISSUER)
		   .withAudience(JWTAuthConstants.JWT_AUDIENCE)
		   .acceptExpiresAt(60)
		   .withClaim(JWTAuthConstants.JWT_PRIVATE_CLAIM_NAME, JWTAuthConstants.JWT_PRIVATE_CLAIM_VALUE)
		   .build();
		
		DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
		
		String jwtID = decodedJWT.getId();
		String jwtValue = connection.sync().get(jwtID);
		if(StringUtils.isBlank(jwtValue))
			return Optional.empty();
		
		JWTRedisDataToken jwtDataToken = new JWTRedisDataToken();
		jwtDataToken.setUserName(decodedJWT.getSubject());
		jwtDataToken.setOtherField(decodedJWT.getClaim(JWTAuthConstants.JWT_OTHER_ATTRIBUTE_CLAIM).asString());
		jwtDataToken.setJwtId(jwtID);
		
		return Optional.ofNullable(jwtDataToken);
		}catch (Exception e) {
			return Optional.empty();
		}
	}

	public void delete(String jwtId) {
		connection.sync().del(jwtId);
	}
	
}
