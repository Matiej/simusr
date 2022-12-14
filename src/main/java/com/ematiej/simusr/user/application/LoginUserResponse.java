package com.ematiej.simusr.user.application;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class LoginUserResponse {
    private Long userId;
    private String username;
    private Set<String> roles;
//    @JsonIgnore
    private String token;

}
