package com.project.project.controllers;

import com.project.project.dtos.JwtRequest;
import com.project.project.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return ResponseEntity.ok(authService.createAuthToken(authRequest));
    }
}
