package com.project.project.controllers;

import com.project.project.dtos.JwtRequest;
import com.project.project.dtos.RegistrationUserDto;
import com.project.project.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//TODO поработать на названиями методов и переменных(не очень устраивать "item", "user")
// может стоит перенести метод register в контроллер юзеров. ведь создание нового пользователя по логике относится больше к user чем к auth контроллеру
@Tag(name = "Контроллер для авторизации и регистрации")
@RestController
@RequiredArgsConstructor
public class AuthController {
private final AuthService authService;

    @Operation(summary = "Метод для авторизации")
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return ResponseEntity.ok(authService.createAuthToken(authRequest));
    }

    @Operation(summary = "Метод для регистрации")
    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return ResponseEntity.ok(authService.createNewUser(registrationUserDto)) ;
    }
}
