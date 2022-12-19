package com.ematiej.simusr.user.controller;

import com.ematiej.simusr.user.application.CreateUserCommand;
import com.ematiej.simusr.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestCreateUserCommand {
    @Email(message = "Wrong email format for 'username' filed")
    private String username;
    @NotBlank(message = "Filed password cannot be empty or null")
    @Size(min = 3, max = 100)
    private String password;
    private List<UserRole> userRoles;

    public CreateUserCommand toCreateUser() {
        return new CreateUserCommand(username, password, Set.copyOf(userRoles));
    }
}
