package com.learning.weblogin.entity;

import java.math.BigInteger;

public interface User {

	public BigInteger getId();

	public void setId(BigInteger id);

	public String getUserName();

	public void setUserName(String userName);

	public String getPassword();

	public void setPassword(String password);

	public String getName();

	public void setName(String name);

	public String getSurname();

	public void setSurname(String surname);
}
