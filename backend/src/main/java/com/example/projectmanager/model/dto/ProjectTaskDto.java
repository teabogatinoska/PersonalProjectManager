package com.example.projectmanager.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectTaskDto {


    private String summary;

    private String taskDescription;

    private String status;

    private Integer priority;

    private String user;

    private Date dueDate;

    private Date created_At;

    private Date updated_At;

    public ProjectTaskDto(String summary, String taskDescription, String status, Integer priority, String username, Date dueDate, Date created_At, Date updated_At) {

        this.summary = summary;
        this.taskDescription = taskDescription;
        this.status = status;
        this.priority = priority;
        this.user = username;
        this.dueDate = dueDate;
        this.created_At = created_At;
        this.updated_At = updated_At;
    }
}
