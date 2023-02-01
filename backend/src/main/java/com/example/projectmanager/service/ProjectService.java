package com.example.projectmanager.service;

import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.User;
import com.example.projectmanager.model.dto.ProjectDto;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Project saveOrUpdateProject(Project project, String leaderUsername, List<User> users);

    Optional<Project> save(ProjectDto projectDto, String leader);

    Optional<Project> edit(ProjectDto projectDto, String leader);


    Project findProjectByIdentifier(String projectId, String username);

    List<Project> findAllProjects(String username);

    List<User> getProjectUsers (Long projectId);

    void deleteProjectByIdentifier(String projectId, String username);

}
