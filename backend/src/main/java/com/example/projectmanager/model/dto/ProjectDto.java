package com.example.projectmanager.model.dto;

import com.example.projectmanager.model.Backlog;
import com.example.projectmanager.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    private Long id;

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Project Identifier is required")
    @Size(min = 4, max = 5, message = "Please use 4 or 5 characteres")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;

    @NotBlank(message = "Project description is required")
    private String description;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Future(message = "Project start date should not be in the past")
    private Date start_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Future(message = "Task End date should not be in the past")
    private Date end_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Future(message = "Project start date should not be in the past")
    @Column(updatable = false)
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    @NotNull
    private Backlog backlog;

    @NotNull
    private List<Long> usersIds;

    private String projectLeader;
}
