package com.insurance.controller;

import com.insurance.dto.AuthRequest;
import com.insurance.dto.AuthResponse;
import com.insurance.response.ApiResponse;
import com.insurance.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid AuthRequest request) {

        String token = customerService.login(request.getEmail(), request.getPassword());

        AuthResponse authResponse = new AuthResponse(token);

        return ResponseEntity.ok(
                ApiResponse.success(authResponse, "Login successful")
        );
    }
}
