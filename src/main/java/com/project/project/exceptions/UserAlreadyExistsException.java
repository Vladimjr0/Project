package com.project.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends ResponseStatusException {
    public UserAlreadyExistsException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
