package com.example.projectmanager.exceptions;


public class TaskEndDateIsNotValidExceptionResponse {

    private String taskEndDate;



    public TaskEndDateIsNotValidExceptionResponse(String taskEndDate) {
        this.taskEndDate = taskEndDate;
    }


    public String getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

}
