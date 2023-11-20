package com.learning.ldapinjection.repository;

import java.math.BigInteger;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.learning.ldapinjection.entity.UserInfo;
import com.learning.ldapinjection.helper.LdapHelper;
import com.learning.weblogin.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);


    private static final String FIND_USER_BY_USERNAME_PASSWORD =
            "(&(uid=%s)(userPassword=%s))";

    private static final String FIND_USER_BY_USERNAME =
            "(uid=%s)";

    private static final String FIND_USER_BY_USER_ID =
            "(uidNumber=%s)";

    private final LdapHelper ldapHelper;

    public UserRepositoryImpl(LdapHelper ldapHelper) {
        this.ldapHelper = ldapHelper;
    }

    @Override
    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
        return queryLdap(FIND_USER_BY_USERNAME_PASSWORD, username, password);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return queryLdap(FIND_USER_BY_USERNAME, username);
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        return queryLdap(FIND_USER_BY_USER_ID, userId);
    }

    private Optional<User> queryLdap(String query, Object... params) {
        Optional<SearchResult> searchResult = ldapHelper.queryLdap(query, params);
        return searchResult.flatMap(result -> Optional.of(getUserFromAttributes(result.getAttributes())));
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
