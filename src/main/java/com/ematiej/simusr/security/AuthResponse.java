package com.ematiej.simusr.security;

import com.ematiej.simusr.user.domain.UserEntity;
import lombok.Value;

@Value
public class AuthResponse {
    UserEntity user;
    String token;
}
