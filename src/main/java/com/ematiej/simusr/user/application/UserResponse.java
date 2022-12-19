package com.ematiej.simusr.user.application;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String username;
    private Set<String> roles;
}
