package com.ematiej.simusr.user.application.port;

import com.ematiej.simusr.user.application.*;

import java.util.List;

public interface UserService {
    LoginUserResponse loginUser(LoginUserCommand command);

    CreateUserResponse register(CreateUserCommand command);

    List<UserResponse> getAll();
}
