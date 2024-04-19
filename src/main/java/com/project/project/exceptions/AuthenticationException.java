package com.project.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthenticationException extends ResponseStatusException {
    public AuthenticationException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }
}
