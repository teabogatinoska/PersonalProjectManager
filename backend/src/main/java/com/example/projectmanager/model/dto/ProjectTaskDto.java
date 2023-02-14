package com.example.projectmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ProjectTaskDto {

    private Long id;

    @NotBlank
    private String summary;

    @NotBlank
    private String taskDescription;

    @NotBlank
    private String status;

    @NotBlank
    private Integer priority;

    @NotBlank
    private String user;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created_At;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated_At;

    public ProjectTaskDto(Long id, String summary, String taskDescription, String status, Integer priority, String username, Date dueDate, Date created_At, Date updated_At) {

        this.id = id;
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
