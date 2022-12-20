package com.ematiej.simusr.security;

import com.ematiej.simusr.security.jwt.JwtTokenFilter;
import com.ematiej.simusr.user.database.UserRepository;
import com.ematiej.simusr.user.domain.UserEntity;
import com.ematiej.simusr.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true
)
public class SecurityConfig {
    private final UserRepository repository;
    private final JwtTokenFilter jwtTokenFilter;

    private final static String[] AUTH_DOC_SWAGGER_PATTERNS = {
            "/swagger-ui/**",
            "/swaggeradmin-openapi/**",
            "/v3/api-docs",
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/configuration/**",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui.html/**",
            "/swagger-ui.html#/**",
            "/swagger-ui.html#/*",
            "/swagger-ui/**",
            "/swagger-ui.html#/swagger-welcome-controller/**",
            "/webjars/springfox-swagger-ui/**",
            "/webjars/**",
    };

    private final static String[] POST_AUTH_ALL_USERS_PATTERNS = {
            "/users",
            "/users/**"
    };

    private final static String[] GET_AUTH_ADMIN = {
            "/users/**",
            "/test/admin/**",
            "/product/init/**"
    };

    private final static String[] GET_AUTH_USER_OR_ADMIN = {
            "/test/useradmin/**",
            "/product/all/**"
    };

    private final static String[] GET_AUTH_USER = {
            "/test/user/**",
    };

    //todo do wywalenia
    @EventListener(ApplicationReadyEvent.class)
    public void saveUser() {
        UserEntity user1 = new UserEntity("maciek@maciek.pl", getBcryptPasswordEncoder().encode("pass123"), Set.of(UserRole.ADMIN));
        UserEntity user2 = new UserEntity("secretary@maciek.pl", getBcryptPasswordEncoder().encode("pass123"), Set.of(UserRole.USER));
        List<UserEntity> userEntities = repository.saveAll(Set.of(user1, user2));
        log.info("Users has been saved successful: " + userEntities.stream().map(UserEntity::getUsername).collect(Collectors.joining(" ; ")));
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);// these exception after that setting is not wrapped by BadCredentialsException anymore
        provider.setPasswordEncoder(getBcryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, POST_AUTH_ALL_USERS_PATTERNS).permitAll()
                .mvcMatchers(AUTH_DOC_SWAGGER_PATTERNS).permitAll()
                .mvcMatchers(HttpMethod.GET, GET_AUTH_USER_OR_ADMIN).hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.GET, GET_AUTH_ADMIN).hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, GET_AUTH_USER).hasRole("USER")
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
