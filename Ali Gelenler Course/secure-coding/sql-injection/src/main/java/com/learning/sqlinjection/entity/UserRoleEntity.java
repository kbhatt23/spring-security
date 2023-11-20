package com.learning.sqlinjection.entity;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.learning.weblogin.entity.UserRole;

import lombok.Data;

@Entity
@Table(name = "user_roles")
@Data
public class UserRoleEntity implements UserRole {

    @Id
    @NotNull
    private BigInteger id;

    @NotNull
    @Column(nullable = false)
    private BigInteger userId;

    @NotNull
    private String role;
}