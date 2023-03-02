package com.learning.resourceserver.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>>{

	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {
		Map<String, Object> realmAccessMap = (Map<String, Object>) jwt.getClaims().get("realm_access");
	
		if(realmAccessMap == null || realmAccessMap.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<String> rolesList = (List<String>) realmAccessMap.get("roles");
		
		return rolesList.stream()
		        .map(role -> "ROLE_"+role)
		        .map(SimpleGrantedAuthority :: new)
		        .collect(Collectors.toList());
	}

}
