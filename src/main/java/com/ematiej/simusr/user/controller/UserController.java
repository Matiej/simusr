package com.ematiej.simusr.user.controller;

import com.ematiej.simusr.global.headerfactory.HeaderKey;
import com.ematiej.simusr.user.application.CreateUserResponse;
import com.ematiej.simusr.user.application.LoginUserResponse;
import com.ematiej.simusr.user.application.UserResponse;
import com.ematiej.simusr.user.application.port.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.ematiej.simusr.global.headerfactory.HttpHeaderFactory.getSuccessfulHeaders;

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
                .header("Message", "User authenticated successful")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.POST.name())
                .body(response);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register new user", description = "Add and register new user. All fields are validated")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User object created successful"),
            @ApiResponse(responseCode = "400", description = "Validation failed. Some fields are wrong. Response contains all details.")
    })
    ResponseEntity<Void> addUser(@RequestBody @Valid RestCreateUserCommand command) {
        CreateUserResponse response = userService.register(command.toCreateUser());
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest()
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.POST.name())
                    .header(HeaderKey.STATUS.getHeaderKeyLabel(), HttpStatus.BAD_REQUEST.name())
                    .header(HeaderKey.MESSAGE.getHeaderKeyLabel(), response.getErrorMessage())
                    .build();
        }
        return ResponseEntity.created(getUri(response.getId()))
                .headers(getSuccessfulHeaders(HttpStatus.CREATED, HttpMethod.POST))
                .build();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping
    @Operation(summary = "Get all users", description = "Get all user from data base. Access only for admin")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation finished successful")
    })
    ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> users = userService.getAll();
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.GET.name())
                .header(HeaderKey.STATUS.getHeaderKeyLabel(), HttpStatus.OK.name())
                .body(users);
    }

    private static URI getUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/users")
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}

