package com.ematiej.simusr.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ematiej.simusr.global.GlobalStatic.ROLE_ADMIN;
import static com.ematiej.simusr.global.GlobalStatic.ROLE_USER;

@Slf4j
@RestController
@RequestMapping(value = "/aa_test")
public class AnnotationAuthTest {

    @Secured(value = {ROLE_USER})
    @GetMapping("/aa_user")
    String getTestUser() {
        log.info("USER TEST FOR AUTH");
        return "1 - Test - only for auth users with ANNOTATION!!!!";
    }

    @Secured(value = {ROLE_ADMIN})
    @GetMapping("/aa_admin")
    String getTestForAdmin() {
        log.info("ADMIN TEST FOR AUTH");
        return "2 - Test - only for auth ADMINS with ANNOTATION!!!!";
    }
}
