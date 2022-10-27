package com.example.projectmanager.exceptions;

public class ProjectIdAlreadyExistsExceptionResponse {
    private String projectIdentifier;

    public ProjectIdAlreadyExistsExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }


}
