package com.learning.springsecurity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.learning.springsecurity.controllers.authentication.BasicAuthUserController;
import com.learning.springsecurity.entity.BasicAclUri;
import com.learning.springsecurity.entity.BasicAuthUser;
import com.learning.springsecurity.repository.BasicAuthUserRepository;
import com.learning.springsecurity.util.EncryptDecryptUtil;
import com.learning.springsecurity.util.HashUtil;
import com.learning.springsecurity.util.SaltGenerator;

//@Component
public class ACLBasicDataSetup implements CommandLineRunner{

	private static final Logger LOG = LoggerFactory.getLogger(ACLBasicDataSetup.class);
	
	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;
	
	@Override
	public void run(String... args) throws Exception {
		LOG.info("basicacl data setup started");
		
		BasicAuthUser basicAuthUser = new BasicAuthUser();
		basicAuthUser.setDisplayName("kbhatt23_admin");
		basicAuthUser.setUsername(EncryptDecryptUtil.encryptAes("kbhatt23@admin.com", BasicAuthUserController.SECRET));
		String salt = SaltGenerator.generateSaltHex();
		basicAuthUser.setSalt(salt);
		basicAuthUser.setPasswordHash(HashUtil.sha256("jaishreeram", salt));
		
		BasicAclUri basicAclUri = new BasicAclUri();
		basicAclUri.setMethod("GET");
		basicAclUri.setUri("/acl/basic");
		
		basicAuthUser.addAcl(basicAclUri, true);
		
		basicAuthUserRepository.save(basicAuthUser);
		
	}

}
