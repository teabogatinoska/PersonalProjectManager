package com.example.projectmanager.service;

import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    Project saveOrUpdateProject(ProjectDto project, String leaderUsername, List<Long> userIds);

    Project findProjectByIdentifier(String projectId, String username);

    List<Project> findAllProjects(String username);

    void deleteProjectByIdentifier(String projectId, String username);

}
