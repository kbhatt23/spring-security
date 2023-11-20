package com.learning.weblogin.entity;

import java.math.BigInteger;
import java.util.UUID;

public interface UserDetail {

	UUID getId();

	void setId(UUID id);

	BigInteger getUserId();

	void setUserId(BigInteger userId);

	String getAddress();

	void setAddress(String address);

	String getCreditCardNo();

	void setCreditCardNo(String creditCardNo);

	String getComment();

	void setComment(String comment);
}
