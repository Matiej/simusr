package com.ematiej.simusr.user.application.port;

import com.ematiej.simusr.user.application.CreateUserCommand;
import com.ematiej.simusr.user.application.CreateUserResponse;
import com.ematiej.simusr.user.application.LoginUserCommand;
import com.ematiej.simusr.user.application.LoginUserResponse;

public interface UserService {
    LoginUserResponse loginUser(LoginUserCommand command);

    CreateUserResponse register(CreateUserCommand command);
}
