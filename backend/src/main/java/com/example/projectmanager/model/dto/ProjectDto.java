package com.example.projectmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class ProjectDto {

    private Long id;

    @NotBlank
    private String projectName;
    @NotBlank
    private String projectIdentifier;
    @NotBlank
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Project start date should not be in the past")
    private Date start_date;

    @Future(message = "Project End date should not be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end_date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created_At;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated_At;

    private List<String> projectUsers;

    private String projectLeader;

    public ProjectDto(Long id, String projectName, String projectIdentifier, String description, Date start_date, Date end_date, Date created_At, Date updated_At, List<String> projectUsers, String projectLeader) {
        this.id = id;
        this.projectName = projectName;
        this.projectIdentifier = projectIdentifier;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_At = created_At;
        this.updated_At = updated_At;
        this.projectUsers = projectUsers;
        this.projectLeader = projectLeader;
    }
}
