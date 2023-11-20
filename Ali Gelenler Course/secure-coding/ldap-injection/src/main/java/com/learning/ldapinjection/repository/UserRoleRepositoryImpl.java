package com.learning.ldapinjection.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.learning.ldapinjection.entity.UserRoleInfo;
import com.learning.ldapinjection.helper.LdapHelper;
import com.learning.weblogin.entity.UserRole;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRoleRepositoryImpl.class);

    private static final String FIND_GROUPS_BY_USER_ID = "(&(objectclass=posixGroup)(memberUid=%s))";

    private final LdapHelper ldapHelper;

    public UserRoleRepositoryImpl(LdapHelper ldapHelper) {
        this.ldapHelper = ldapHelper;
    }

    @Override
    public Optional<List<UserRole>> findUserRoleByUserId(String uid) {
        Optional<SearchResult> searchResult = ldapHelper.queryLdap(FIND_GROUPS_BY_USER_ID, uid);
        return searchResult.flatMap(result -> Optional.of(getGroupsFromAttributes(result.getAttributes())));
    }


    private List<UserRole> getGroupsFromAttributes(Attributes attributes) {
        List<UserRole> groups = new ArrayList<>();
        try {
            Attribute attributeCn = attributes.get("cn");
            for (int i = 0; i < attributeCn.size(); i++) {
                String role = attributeCn.get(i).toString();
                UserRoleInfo userRoleInfo = new UserRoleInfo();
                userRoleInfo.setRole(role);
                groups.add(userRoleInfo);
                LOG.debug("User is member of group {} ", role);
            }
        } catch (NamingException e) {
            LOG.error("Couldn't set user groups: {}", e.getMessage());
            throw new RuntimeException("Couldn't set user groups: {}");
        }
        return groups;
    }

}
