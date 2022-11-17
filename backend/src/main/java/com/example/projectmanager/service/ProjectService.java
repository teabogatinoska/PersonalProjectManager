package com.example.projectmanager.service;

import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.User;

import java.util.List;
import java.util.Set;

public interface ProjectService {

    Project saveOrUpdateProject(Project project, String leaderUsername, Set<User> users);

    Project findProjectByIdentifier(String projectId, String username);

    List<Project> findAllProjects(String username);

    void deleteProjectByIdentifier(String projectId, String username);

}
