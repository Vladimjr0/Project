package com.project.project.controllers;

import com.project.project.dtos.JwtRequest;
import com.project.project.dtos.JwtResponse;
import com.project.project.exceptions.AppError;
import com.project.project.services.UserService;
import com.project.project.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    private final UserService userService;


    private final JwtTokenUtils jwtTokenUtils;


    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, JwtTokenUtils jwtTokenUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "ИДИ НАХУЙ"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
