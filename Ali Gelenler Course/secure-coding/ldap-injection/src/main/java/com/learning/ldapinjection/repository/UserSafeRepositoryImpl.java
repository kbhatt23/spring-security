package com.learning.ldapinjection.repository;

import java.math.BigInteger;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Repository;

import com.learning.ldapinjection.entity.UserInfo;
import com.learning.weblogin.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserSafeRepositoryImpl implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final LdapTemplate ldapTemplate;

    public UserSafeRepositoryImpl(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	@Override
    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
		return ldapTemplate.search(LdapQueryBuilder.query()
				.searchScope(SearchScope.SUBTREE)
				.where("uid").is(username)
				.and("userPassword").is(password)
				, this :: getUserFromAttributes).stream().findFirst();
	}

    @Override
    public Optional<User> findUserByUsername(String username) {
    	return ldapTemplate.search(LdapQueryBuilder.query()
    			.searchScope(SearchScope.SUBTREE)
    			.where("uid").is(username)
    			, this :: getUserFromAttributes)
    			.stream().findFirst();
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
    	return ldapTemplate.search(LdapQueryBuilder.query()
    			.searchScope(SearchScope.SUBTREE)
    			.where("uidNumber").is(userId)
    			, this :: getUserFromAttributes)
    			.stream().findFirst();
    }

    private User getUserFromAttributes(Attributes attributes) {
        User user = new UserInfo();
        try {
            if (attributes.get("uidNumber") != null) {
                user.setId(new BigInteger(attributes.get("uidNumber").get().toString()));
            }
            if (attributes.get("uid") != null) {
                user.setUserName(attributes.get("uid").get().toString());
            }
            if (attributes.get("userPassword") != null) {
                user.setPassword(attributes.get("userPassword").get().toString());
            }
            if (attributes.get("givenName") != null) {
                user.setName(attributes.get("givenName").get().toString());
            }
            if (attributes.get("sn") != null) {
                user.setSurname(attributes.get("sn").get().toString());
            }
        } catch (NamingException e) {
            LOG.error("Couldn't set user attributes: {}", e.getMessage());
            throw new RuntimeException("Couldn't set user attributes");
        }
        return user;
    }


}
