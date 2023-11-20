package com.learning.sqlinjection.entity;

import java.math.BigInteger;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.learning.weblogin.entity.UserDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailEntity implements UserDetail {

	@Id
	@NotNull
	private UUID id;

	@NotNull
	private BigInteger userId;

	@NotNull
	private String address;

	@NotNull
	private String creditCardNo;

	@NotNull
	private String comment;
}
