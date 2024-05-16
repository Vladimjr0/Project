package com.project.project.controllers;

import com.project.project.dtos.JwtRequest;
import com.project.project.dtos.JwtResponse;
import com.project.project.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Контроллер для авторизации")
@RestController
@RequiredArgsConstructor
public class AuthController {
private final AuthService authService;

    @Operation(summary = "Метод для авторизации")
    @PostMapping("/auth")
    @PermitAll
    public ResponseEntity<JwtResponse> createAuthToken(@Valid @RequestBody JwtRequest authRequest) {
        return ResponseEntity.ok(authService.createAuthToken(authRequest));
    }
}
