package com.ematiej.simusr.security.port;

import com.ematiej.simusr.security.AuthResponse;
import com.ematiej.simusr.security.AuthCommand;

public interface UserSecurityService {

    AuthResponse authorize(AuthCommand command);
}
