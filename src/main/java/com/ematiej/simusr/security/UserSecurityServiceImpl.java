package com.ematiej.simusr.security;

import com.ematiej.simusr.security.port.UserSecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Override
    public AuthResponse authorize(AuthCommand command) {
        return null;
    }

    public boolean isOwnerOrAdmin(String objectOwner, UserDetails userDetails) {
        return isAdmin(userDetails) || isOwner(objectOwner, userDetails);
    }

    private boolean isOwner(String objectOwner, UserDetails userDetails) {
        return StringUtils.equalsIgnoreCase(objectOwner, userDetails.getUsername());
    }

    private boolean isAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(a -> StringUtils.equalsIgnoreCase(a.getAuthority(), ROLE_ADMIN));
    }
}
