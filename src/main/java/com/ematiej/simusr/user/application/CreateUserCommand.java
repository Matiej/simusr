package com.ematiej.simusr.user.application;

import com.ematiej.simusr.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserCommand {
    private String username;
    private String password;
    private Set<UserRole> userRoles;
}
