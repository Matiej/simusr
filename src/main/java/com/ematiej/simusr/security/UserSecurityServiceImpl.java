package com.ematiej.simusr.security;

import com.ematiej.simusr.security.port.UserSecurityService;
import com.ematiej.simusr.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse authorize(AuthCommand command) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserEntityDetails principal = (UserEntityDetails) authenticate.getPrincipal();
        return new AuthResponse(ResponseCookie.from("Maja", "sada").build(), principal.getUserEntity());
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
