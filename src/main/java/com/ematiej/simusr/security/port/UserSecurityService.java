package com.ematiej.simusr.security.port;

import com.ematiej.simusr.security.AuthCommand;
import com.ematiej.simusr.security.AuthResponse;

public interface UserSecurityService {

    AuthResponse authorize(AuthCommand command);
}
