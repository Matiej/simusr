package com.ematiej.simusr.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtService {
    private static final String CLAIM = "ROLES";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.ms}")
    private int jwtExpirationMs;

    @Value("${jwt.issuer}")
    private String jwtIssuer;


    public String generateJWTToken(String subjectUserName, String roles) {
        Algorithm algorithm = getAlgorithm();
        return JWT.create()
                .withSubject(subjectUserName)
                .withIssuer(jwtIssuer)
                .withIssuedAt(new Date())
                .withClaim(CLAIM, roles)
                .withExpiresAt(new Date(new Date().getTime() + jwtExpirationMs))
                .sign(algorithm);
    }

    public UsernamePasswordAuthenticationToken getUsernameAndPasswordToken(String token) throws IllegalArgumentException {
        JWTVerifier jwtVerifier = JWT.require(getAlgorithm())
                .build();
        String subStringToken = subStringToken(token);
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(subStringToken);
            String roles = decodedJWT.getClaim(CLAIM).asString();
            Set<? extends GrantedAuthority> authorities = getAuthorities(roles);

            return new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong JWT token: " + e.getMessage());
        }

    }

    private String subStringToken(String token) {
        if (token.contains(jwtSecret)) {
            return StringUtils.substringAfter(token, jwtIssuer).trim();
        }
        return StringUtils.substringAfter(token, "Bearer").trim();
    }

    private Algorithm getAlgorithm() {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return algorithm;
    }

    private Set<? extends GrantedAuthority> getAuthorities(String roles) {
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
