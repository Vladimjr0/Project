package com.project.project.controllers;

import com.project.project.exceptions.AuthenticationException;
import com.project.project.exceptions.PasswordMismatchException;
import com.project.project.exceptions.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handeResponseStatusException(ResponseStatusException e){
        return new ResponseEntity<>(e.getReason(),e.getStatusCode());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> passwordMismatchException(PasswordMismatchException e){
        return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> authenticationException(AuthenticationException e){
        return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> userAlreadyExistsException(UserAlreadyExistsException e){
        return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }
}
