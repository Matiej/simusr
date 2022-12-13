package com.ematiej.simusr.security;

import com.ematiej.simusr.user.domain.UserEntity;
import lombok.Value;
import org.springframework.http.ResponseCookie;

@Value
public class AuthResponse {
    ResponseCookie cookie;
    UserEntity user;
}
