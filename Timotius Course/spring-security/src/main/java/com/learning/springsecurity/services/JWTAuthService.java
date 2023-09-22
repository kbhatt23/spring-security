package com.learning.springsecurity.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.learning.springsecurity.constants.JWTAuthConstants;
import com.learning.springsecurity.dtos.JWTDataToken;

@Service
public class JWTAuthService {
	
	public String store(JWTDataToken jwtDataToken) {
		var algo = Algorithm.HMAC256(JWTAuthConstants.JWT_HMAC_SECRET);
		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
		LocalDateTime expiry = now.plusMinutes(JWTAuthConstants.JWT_EXPIRY_MINUTES);
		Date currentDate = Date.from(now.toInstant(ZoneOffset.UTC));
		
		return JWT.create()
		   .withSubject(jwtDataToken.getUserName())
		   .withIssuer(JWTAuthConstants.JWT_ISSUER)
		   .withAudience(JWTAuthConstants.JWT_AUDIENCE)
		   .withExpiresAt(Date.from(expiry.toInstant(ZoneOffset.UTC)))
		   .withClaim(JWTAuthConstants.JWT_PRIVATE_CLAIM_NAME, JWTAuthConstants.JWT_PRIVATE_CLAIM_VALUE)
		   .withClaim(JWTAuthConstants.JWT_OTHER_ATTRIBUTE_CLAIM, jwtDataToken.getOtherField())
		   .withIssuedAt(currentDate)
		   .withNotBefore(currentDate)
		   .sign(algo);
	}
	
	public Optional<JWTDataToken> read(String jwtToken){
		try {
		var algo = Algorithm.HMAC256(JWTAuthConstants.JWT_HMAC_SECRET);
		
		JWTVerifier jwtVerifier = JWT.require(algo)
		   .withIssuer(JWTAuthConstants.JWT_ISSUER)
		   .withAudience(JWTAuthConstants.JWT_AUDIENCE)
		   .acceptExpiresAt(60)
		   .withClaim(JWTAuthConstants.JWT_PRIVATE_CLAIM_NAME, JWTAuthConstants.JWT_PRIVATE_CLAIM_VALUE)
		   .build();
		
		DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
		
		JWTDataToken jwtDataToken = new JWTDataToken();
		jwtDataToken.setUserName(decodedJWT.getSubject());
		jwtDataToken.setOtherField(decodedJWT.getClaim(JWTAuthConstants.JWT_OTHER_ATTRIBUTE_CLAIM).asString());
		
		
		return Optional.ofNullable(jwtDataToken);
		}catch (Exception e) {
			return Optional.empty();
		}
	}
	
}
