package com.ematiej.simusr.security.jwt;

import com.ematiej.simusr.global.exceptionhandler.RestControllerExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final RestControllerExceptionHandler restControllerExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (StringUtils.isNotBlank(token)) {
                UsernamePasswordAuthenticationToken usernameAndPasswordToken = jwtService.getUsernameAndPasswordToken(token);
                SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordToken);
            }
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            ResponseEntity<Object> errorJson = restControllerExceptionHandler.handleJWTTokenFilterException(e, request);
            response.getWriter().write(convertErrorObject(errorJson));
        }
    }

    private String convertErrorObject(ResponseEntity<Object> errorObj) throws JsonProcessingException {
        if (errorObj == null) {
            return null;
        }
        ObjectMapper mapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        return mapper.writeValueAsString(errorObj);
    }
}

