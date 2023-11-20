package com.learning.sqlinjection.repository.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.learning.sqlinjection.entity.UserEntity;
import com.learning.sqlinjection.repository.CustomUserRepository;
import com.learning.weblogin.entity.User;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRepositoryImpl implements CustomUserRepository {

	private static final String FIND_USER_BY_USERNAME_PASSWORD = "select id, username, password, name, surname "
			+ "from users u " + "where u.username = '%s' " + "and u.password = '%s'";

	private static final String FIND_USER_BY_USERNAME = "select id, username, password, name, surname "
			+ "from users u " + "where u.username = '%s'";

	private static final String FIND_USER_BY_USERID = "select id, username, password, name, surname " + "from users u "
			+ "where u.id = %s";

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<User> findUserByUserNameAndPassword(String userName, String password) {
		return queryDatabase(FIND_USER_BY_USERNAME_PASSWORD, userName, password);
	}

	@Override
	public Optional<User> findUserByUserName(String username) {
		return queryDatabase(FIND_USER_BY_USERNAME, username);
	}

	@Override
	public Optional<User> findUserByUserId(String userId) {
		return queryDatabase(FIND_USER_BY_USERID, userId);
	}

	private Optional<User> queryDatabase(String query, Object... args) {
		String formattedSQL = String.format(query, args);
		Query nativeQuery = em.createNativeQuery(formattedSQL);
		log.debug("Querying user with {}", nativeQuery);

		//to be safe from scenario when username/password both is unknow and still able to login
		//it returns all the users and thios code takes first one and use that for session context
		//abc/abc' or '1'='1 are the credentials
		List<Object[]> result = nativeQuery.getResultList();
		if (result == null || result.size() == 0) {
			return Optional.empty();
		}		return Optional.of(mapResultToUser(result.get(0)));
		
//		Object result = nativeQuery.getSingleResult();
//		if(result == null)
//			return Optional.empty();
//		
//		return Optional.of(mapResultToUser(result));
		
	}

	private User mapResultToUser(Object[] result) {
		UserEntity user = new UserEntity();
		user.setId(new BigInteger(result[0].toString()));
		user.setUserName(result[1].toString());
		user.setPassword(result[2].toString());
		user.setName(result[3].toString());
		user.setSurname(result[4].toString());
		return user;
	}
	private User mapResultToUser(Object result) {
		UserEntity user = new UserEntity();
		user.setId(new BigInteger(result.toString()));
		user.setUserName(result.toString());
		user.setPassword(result.toString());
		user.setName(result.toString());
		user.setSurname(result.toString());
		return user;
	}

}
