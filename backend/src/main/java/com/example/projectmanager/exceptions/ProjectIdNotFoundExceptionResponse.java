package com.example.projectmanager.exceptions;

public class ProjectIdNotFoundExceptionResponse {

    private String projectIdentifier;

    public ProjectIdNotFoundExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }


}

