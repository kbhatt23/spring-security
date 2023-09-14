package com.learning.springsecurity.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
//optional as naming convention picks the same table name itself
@Table(name = "basic_auth_user")
@Data
public class BasicAuthUser {

	//column names in pojo is created based on the column name in table
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	private String username;
	
	private String passwordHash;
	
	private String salt;
	
	private String displayName;
}
