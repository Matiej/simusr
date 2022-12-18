package com.ematiej.simusr.user.domain;

import com.ematiej.simusr.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    private String username;
    @JsonIgnore
    private String password;
    private String roles;

    public UserEntity(String username, String password, Set<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.roles = userRoles.stream().map(UserRole::name).collect(Collectors.joining(","));

    }
}

