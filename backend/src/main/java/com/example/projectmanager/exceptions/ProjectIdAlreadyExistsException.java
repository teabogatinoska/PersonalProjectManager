package com.example.projectmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdAlreadyExistsException extends RuntimeException {

    public ProjectIdAlreadyExistsException(String message) {
        super(message);
    }
}
