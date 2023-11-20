package com.learning.weblogin.entity;

import java.math.BigInteger;

public interface UserRole {

	public BigInteger getId();

	public void setId(BigInteger id);
	
	public BigInteger getUserId();
	
	public void setUserId(BigInteger userId);
	
	public String getRole();
	
	public void setRole(String role);
	
}
