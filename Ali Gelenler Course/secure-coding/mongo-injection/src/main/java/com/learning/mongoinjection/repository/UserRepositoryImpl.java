package com.learning.mongoinjection.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import com.learning.mongoinjection.collections.UserDocument;
import com.learning.weblogin.entity.User;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRepositoryImpl implements CustomUserRepository {

    private static final String FIND_USER_BY_USERNAME_PASSWORD =
            "{$where: 'function() { return this.userName == \"%s\" && this.password == \"%s\"}'}";

    private static final String FIND_USER_BY_USERNAME =
            "{$where: 'function() { return this.userName == \"%s\"}'}";

    private static final String FIND_USER_BY_USERID =
            "{_id : %s}";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<User> findUserByUserNameAndPassword(String username, String password) {
        return queryDatabase(FIND_USER_BY_USERNAME_PASSWORD, username, password);
    }

    @Override
    public Optional<User> findUserByUserName(String username) {
        return queryDatabase(FIND_USER_BY_USERNAME, username);
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        return queryDatabase(FIND_USER_BY_USERID, userId);
    }

    private Optional<User> queryDatabase(String query, Object... params) {
        String formattedQuery = String.format(query, params);
        log.info("Querying user with {}", formattedQuery);
        BasicQuery basicQuery = new BasicQuery(formattedQuery);
        List<UserDocument> result = mongoTemplate.find(basicQuery, UserDocument.class);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

}