package com.example.projectmanager.service;

import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.User;
import com.example.projectmanager.model.dto.ProjectDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectService {

    Optional<Project> save(ProjectDto projectDto, String leader);

    Optional<Project> edit(ProjectDto projectDto, String leader);


    Project findProjectByIdentifier(String projectId, String username);

    List<Project> findAllProjects(String username);

    Set<User> getProjectUsers (Long projectId);

    void deleteProjectByIdentifier(String projectId, String username);

}
