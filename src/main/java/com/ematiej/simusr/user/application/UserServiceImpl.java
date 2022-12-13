package com.ematiej.simusr.user.application;

import com.ematiej.simusr.security.AuthCommand;
import com.ematiej.simusr.security.AuthResponse;
import com.ematiej.simusr.security.port.UserSecurityService;
import com.ematiej.simusr.user.application.port.UserService;
import com.ematiej.simusr.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class UserServiceImpl implements UserService {
    private final UserSecurityService userSecurityService;

    @Override
    public LoginUserResponse loginUser(LoginUserCommand command) {
        AuthCommand authCommand = new AuthCommand(command.getUsername(), command.getPassword());
        AuthResponse authResponse = userSecurityService.authorize(authCommand);
        UserEntity user = authResponse.getUser();
        Set<String> roles = Arrays.stream(user.getRoles().split(","))
                .collect(Collectors.toSet());
        return LoginUserResponse.builder()
                .username(user.getUsername())
                .userId(user.getId())
                .roles(roles)
                .responseCookie(authResponse.getCookie())
                .build();
    }
}
