package com.ematiej.simusr.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
        UserEntityDetails principal = (UserEntityDetails) authenticate.getPrincipal();
        UserEntity user = principal.getUserEntity();

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String jwtToken = generateJWTToken(command.getUsername(), user.getRoles());

        return new AuthResponse(user, jwtToken);
    }

    private String generateJWTToken(String subjectUserName, String roles) {
        Algorithm algorithm = Algorithm.HMAC256("secret_matiej");
        return JWT.create()
                .withSubject(subjectUserName)
                .withIssuer("eMatiej")
                .withClaim("ROLES", roles)
                .sign(algorithm);
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
