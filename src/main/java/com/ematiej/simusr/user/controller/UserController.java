package com.ematiej.simusr.user.controller;

import com.ematiej.simusr.user.application.LoginUserResponse;
import com.ematiej.simusr.user.application.port.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users API", description = "API designed to manipulate the object user.")
//@SecurityRequirement(name = "springrecallbook-api_documentation")
class UserController {
    private final UserService userService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoginUserResponse> loginUser(@RequestBody @Valid RestLoginUser restLoginUser) {
        log.info("Request login for user: " + restLoginUser.getUsername());
        LoginUserResponse response = userService.loginUser(restLoginUser.toLoginUserCommand());
        return ResponseEntity.status(HttpStatus.OK)
                .header("Status", HttpStatus.OK.name())
//                .header(HttpHeaders.SET_COOKIE, response.getResponseCookie().toString())
                .header("Message", "User authenticated successful")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.POST.name())
                .body(response);
    }
}
