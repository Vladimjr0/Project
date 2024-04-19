package com.project.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordMismatchException extends ResponseStatusException {
    public PasswordMismatchException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
