package com.ematiej.simusr.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/test")
public class AuthTestController {

    @GetMapping("/user")
    ResponseEntity<Object> getTestUser() {
        log.info("USER TEST FOR AUTH");
        return ResponseEntity.ok().body("1 - Test - only for auth users");
    }

    @GetMapping("/admin")
    ResponseEntity<Object> getTestForAdmin() {
        log.info("ADMIN TEST FOR AUTH");
        return ResponseEntity.ok().body("2 - Test - only for auth ADMINS");
    }

    @GetMapping("/useradmin")
    ResponseEntity<Object> getTestForAdminOrUser() {
        log.info("USER & ADMIN TEST FOR AUTH");
        return ResponseEntity.ok().body("3 - Test - only for auth USER OR ADMINS");
    }

}
