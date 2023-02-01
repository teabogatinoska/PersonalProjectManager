package com.example.projectmanager.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectDto {

    private Long id;
    private String projectName;
    private String projectIdentifier;
    private String description;
    private Date start_date;
    private Date end_date;
    private Date created_At;
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
