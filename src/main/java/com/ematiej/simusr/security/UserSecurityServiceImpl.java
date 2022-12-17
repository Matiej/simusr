package com.ematiej.simusr.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ematiej.simusr.security.jwt.JwtService;
import com.ematiej.simusr.security.port.UserSecurityService;
import com.ematiej.simusr.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
    private final JwtService jwtService;

    @Override
    public AuthResponse authorize(AuthCommand command) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword()));
        UserEntityDetails principal = (UserEntityDetails) authenticate.getPrincipal();
        UserEntity user = principal.getUserEntity();

        String jwtToken = jwtService.generateJWTToken(command.getUsername(), user.getRoles());

        return new AuthResponse(user, jwtToken);
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
