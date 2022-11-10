package com.example.projectmanager.exceptions;

public class InvalidUserPermissionsExceptionResponse {

    String message;

    public InvalidUserPermissionsExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
