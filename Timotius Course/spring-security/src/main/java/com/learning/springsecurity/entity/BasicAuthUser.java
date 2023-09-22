package com.learning.springsecurity.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, 
			//if we do n set mapped by here , it will create two table one project_progarmmer and another programmer_projects
			//mapped by ignores table creation form this entity side
			mappedBy = "users", fetch = FetchType.EAGER
			)
	private List<BasicAclUri> aclUris;
	
	public void addAcl(BasicAclUri aclUri, boolean updateOnBothEntities) {
		if (aclUri == null) {
			return;
		}
		if (aclUris == null) {
			aclUris = new ArrayList<>();
		}
		aclUris.add(aclUri);
		if (updateOnBothEntities) {
			aclUri.addUser(this, false);
		}
	}
}
