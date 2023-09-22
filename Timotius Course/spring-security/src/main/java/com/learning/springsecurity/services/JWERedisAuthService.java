package com.learning.springsecurity.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.springsecurity.constants.JWTAuthConstants;
import com.learning.springsecurity.dtos.JWERedisDataToken;
import com.learning.springsecurity.util.SaltGenerator;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

import io.lettuce.core.api.StatefulRedisConnection;

@Service
public class JWERedisAuthService {
	
	@Autowired
	private StatefulRedisConnection<String, String> connection;
	
	public String store(JWERedisDataToken jweData) {
		try {
			var expiresAt = LocalDateTime.now(ZoneOffset.UTC).plusMinutes(JWTAuthConstants.JWT_EXPIRY_MINUTES);
			
			String jwtId = SaltGenerator.generateSaltHex(32);
			connection.sync().setex(jwtId, JWTAuthConstants.JWT_EXPIRY_MINUTES * 60,jweData.getUserName());//store just userName
	
			var jweClaims = new JWTClaimsSet.Builder()
					                        .expirationTime(Date.from(expiresAt.toInstant(ZoneOffset.UTC)))
					                        .issuer(JWTAuthConstants.JWT_ISSUER)
					                        .audience(Arrays.asList(JWTAuthConstants.JWT_AUDIENCE))
					                        .subject(jweData.getUserName())
					                        .claim(JWTAuthConstants.JWT_OTHER_ATTRIBUTE_CLAIM, jweData.getOtherField())
					                        .claim(JWTAuthConstants.JWT_PRIVATE_CLAIM_NAME, JWTAuthConstants.JWT_PRIVATE_CLAIM_VALUE)
					                        .jwtID(jwtId)
					                        .build();
			var jweHeader = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A256GCM);
	
			var jwe = new EncryptedJWT(jweHeader, jweClaims);
	
			var encrypter = new DirectEncrypter(JWTAuthConstants.JWE_ENCRYPTION_KEY.getBytes());
			jwe.encrypt(encrypter);
	
			return jwe.serialize();
		}
		catch (JOSEException e) {
			return StringUtils.EMPTY;
		}
	}
	
	public Optional<JWERedisDataToken> read(String jweToken) {
		try {
			var jwe = EncryptedJWT.parse(jweToken);

			var decryptor = new DirectDecrypter(JWTAuthConstants.JWE_ENCRYPTION_KEY.getBytes());
			jwe.decrypt(decryptor);
			var jweClaims = jwe.getJWTClaimsSet();

			var now = new Date();

			if (now.before(jweClaims.getExpirationTime())
					&& StringUtils.equals(jweClaims.getStringClaim(JWTAuthConstants.JWT_PRIVATE_CLAIM_NAME), JWTAuthConstants.JWT_PRIVATE_CLAIM_VALUE)
					&& StringUtils.equals(jweClaims.getIssuer(), JWTAuthConstants.JWT_ISSUER)
					&& Arrays.asList(JWTAuthConstants.JWT_AUDIENCE).equals(jweClaims.getAudience())
					) {
				
				String jwtID = jweClaims.getJWTID();
				String jwtValue = connection.sync().get(jwtID);
				if(StringUtils.isBlank(jwtValue))
					return Optional.empty();
				
				var jweData = new JWERedisDataToken();
				jweData.setUserName(jweClaims.getSubject());
				jweData.setOtherField(jweClaims.getStringClaim(JWTAuthConstants.JWT_OTHER_ATTRIBUTE_CLAIM));
				jweData.setJwtId(jwtID);
				
				return Optional.of(jweData);
			}

			return Optional.empty();
		} catch (Exception ex) {
			return Optional.empty();
		}
	}
	
	public void delete(String jwtId) {
		connection.sync().del(jwtId);
	}
	
}
