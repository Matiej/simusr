package com.ematiej.simusr.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/test")
public class AuthTestController {

    @GetMapping("/user")
    @Secured(value = { "ROLE_USER"})
    String getTestUser() {
        log.info("USER TEST FOR AUTH");
        return "1 - Test - only for auth users";
    }

    @GetMapping("/admin")
    @Secured(value = {"ROLE_ADMIN"})
    String getTestForAdmin() {
        log.info("ADMIN TEST FOR AUTH");
        return "2 - Test - only for auth ADMINS";
    }

    @GetMapping("/useradmin")
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    String getTestForAdminOrUser() {
        log.info("USER & ADMIN TEST FOR AUTH");
        return "3 - Test - only for auth USER OR ADMINS";
    }

}
