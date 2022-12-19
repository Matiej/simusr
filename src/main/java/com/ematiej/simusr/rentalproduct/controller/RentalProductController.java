package com.ematiej.simusr.rentalproduct.controller;

import com.ematiej.simusr.global.headerfactory.HeaderKey;
import com.ematiej.simusr.rentalproduct.application.port.RentalProductService;
import com.ematiej.simusr.rentalproduct.domain.Motorcycle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ematiej.simusr.global.GlobalStatic.ROLE_ADMIN;
import static com.ematiej.simusr.global.GlobalStatic.ROLE_USER;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "Rental products API", description = "API designed to manipulate the object rentalProducts.")
public class RentalProductController {
    private final RentalProductService rentalProductService;

    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("/all")
    @Operation(summary = "Get all users", description = "Get all user from data base. Access only for admin")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation finished successful")
    })
    ResponseEntity<List<Motorcycle>> getAllMotorcycles() {
        List<Motorcycle> motorcycles = rentalProductService.getAllMotorcycles();
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.GET.name())
                .header(HeaderKey.STATUS.getHeaderKeyLabel(), HttpStatus.OK.name())
                .body(motorcycles);
    }

    @Secured(value = {ROLE_ADMIN})
    @GetMapping("/init")
    @Operation(summary = "Init some products", description = "Initialize some example test motorcycle rental products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation finished successful")
    })
    ResponseEntity<Void> init() {
        rentalProductService.init();
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.GET.name())
                .header(HeaderKey.STATUS.getHeaderKeyLabel(), HttpStatus.OK.name())
                .build();
    }
}
