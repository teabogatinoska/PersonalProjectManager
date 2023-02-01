package com.example.projectmanager.exceptions;

public class UserIdNotFoundExceptionResponse {

    private String UserNotFound;

    public UserIdNotFoundExceptionResponse(String UserNotFound) {
        UserNotFound = UserNotFound;
    }

    public String getUserNotFound() {
        return UserNotFound;
    }

    public void setUserNotFound(String userNotFound) {
        UserNotFound = userNotFound;
    }
}
