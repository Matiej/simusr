package com.ematiej.simusr.user.application;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class LoginUserResponse extends UserResponse {
    private String token;

    public LoginUserResponse(Long userId, String username, Set<String> roles, String token) {
        super(userId, username, roles);
        this.token = token;
    }
}
