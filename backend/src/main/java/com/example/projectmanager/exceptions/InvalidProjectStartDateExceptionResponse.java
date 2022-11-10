package com.example.projectmanager.exceptions;

public class InvalidProjectStartDateExceptionResponse {

    private String startDate;

    public InvalidProjectStartDateExceptionResponse(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
