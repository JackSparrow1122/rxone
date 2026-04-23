package com.gryphon.rxone.controller;

import com.gryphon.rxone.DTO.Auth.AuthResponse;
import com.gryphon.rxone.DTO.Auth.LoginRequest;
import com.gryphon.rxone.DTO.Auth.RegisterRequest;
import com.gryphon.rxone.DTO.Auth.ResetPasswordRequest;
import com.gryphon.rxone.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PatchMapping("/reset-password")
    @PreAuthorize("isAuthenticated()")
    public AuthResponse resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return authService.resetPassword(request);
    }
}