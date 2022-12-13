package com.ematiej.simusr.user.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseCookie;

import java.util.Set;

@Data
@Builder
public class LoginUserResponse {
    private Long userId;
    private String username;
    private Set<String> roles;
    @JsonIgnore
    private ResponseCookie responseCookie;

}
