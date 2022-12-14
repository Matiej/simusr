package com.ematiej.simusr.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class AuthTestController {

    @GetMapping
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    String getTest() {
        return "Test - only for auth users";
    }
}
