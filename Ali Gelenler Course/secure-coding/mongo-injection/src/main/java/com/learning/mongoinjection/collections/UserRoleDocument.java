package com.learning.mongoinjection.collections;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.learning.weblogin.entity.UserRole;

import lombok.Data;

@Data
@Document(collection = "user_roles")
public class UserRoleDocument implements UserRole {

    @Id
    @NotNull
    private BigInteger id;

    @NotNull
    private BigInteger userId;

    @NotNull
    private String role;
}
