package com.ematiej.simusr.user.controller;

import com.ematiej.simusr.user.application.LoginUserCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestLoginUser {
    @Email(message = "Wrong email format for 'username' filed")
    private String username;
    @NotBlank(message = "Filed password cannot be empty or null")
    @Size(min = 3, max = 100)
    private String password;

    public LoginUserCommand toLoginUserCommand() {
        return new LoginUserCommand(username, password);
    }
}
