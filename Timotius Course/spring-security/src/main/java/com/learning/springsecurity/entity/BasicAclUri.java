package com.learning.springsecurity.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class BasicAclUri {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uri_id")
	private Integer uriId;
	
	private String method;
	
	private String uri;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "basic_acl_user_uri_ref" , joinColumns = @JoinColumn(name = "uri_id"),
		inverseJoinColumns  = @JoinColumn(name ="user_id" )
			)
	private List<BasicAuthUser> users;
	
	public void addUser(BasicAuthUser user, boolean updateOnBothEntities) {
		if (user == null) {
			return;
		}
		if (users == null) {
			users = new ArrayList<>();
		}
		users.add(user);
		if (updateOnBothEntities) {
			user.addAcl(this, false);
		}
	}
}
