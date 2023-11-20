package com.learning.ldapinjection.repository;

import java.util.List;
import java.util.Optional;

import com.learning.weblogin.entity.UserRole;

public interface UserRoleRepository {

	Optional<List<UserRole>> findUserRoleByUserId(String uid);
}
