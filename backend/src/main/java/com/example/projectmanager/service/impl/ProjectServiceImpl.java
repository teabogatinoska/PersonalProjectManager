package com.example.projectmanager.service.impl;

import com.example.projectmanager.exceptions.ProjectIdNotFoundException;
import com.example.projectmanager.exceptions.ProjectNotFoundException;
import com.example.projectmanager.model.Backlog;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.User;
import com.example.projectmanager.model.dto.ProjectDto;
import com.example.projectmanager.repository.BacklogRepository;
import com.example.projectmanager.repository.ProjectRepository;
import com.example.projectmanager.repository.UserRepository;
import com.example.projectmanager.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final BacklogRepository backlogRepository;

    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, BacklogRepository backlogRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Project> save(ProjectDto projectDto, String leader) {
        Project project = new Project();
        Backlog backlog = new Backlog();
        Set<User> projectUsers = new HashSet<>();
        User leaderUser = this.userRepository.findByUsername(leader);

        for (String username : projectDto.getProjectUsers()) {
            projectUsers.add(this.userRepository.findByUsername(username));
        }
        Date currentDate = new java.util.Date();

        project.setProjectIdentifier(projectDto.getProjectIdentifier().toUpperCase());
        project.setBacklog(backlog);
        backlog.setProject(project);
        backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
        project.setProjectUsers(projectUsers);
        project.setProjectName(projectDto.getProjectName());
        project.setDescription(projectDto.getDescription());
        project.setStart_date(currentDate);
        project.setEnd_date(projectDto.getEnd_date());
        project.setProjectLeader(leaderUser.getUsername());

        this.projectRepository.save(project);


        return Optional.of(project);
    }

    @Override
    public Optional<Project> edit(ProjectDto projectDto, String leader) {
        Project existingProject;

        if (projectDto.getId() != null) {
            existingProject = this.projectRepository.findByProjectIdentifier(projectDto.getProjectIdentifier());
        } else
            throw new ProjectNotFoundException("Project with ID:'" + projectDto.getProjectIdentifier() + "' cannot be updated because it does not exist!");

        User leaderUser = this.userRepository.findByUsername(leader);
        Backlog backlog = this.backlogRepository.findByProjectIdentifier(projectDto.getProjectIdentifier().toUpperCase());
        Set<User> userList = new HashSet<>();


        for (String username : projectDto.getProjectUsers()) {
            userList.add(this.userRepository.findByUsername(username));
        }
        if (!userList.contains(leaderUser)) {
            userList.add(leaderUser);
        }
        existingProject.setProjectLeader(leaderUser.getUsername());
        existingProject.setProjectIdentifier(projectDto.getProjectIdentifier().toUpperCase());
        existingProject.setProjectUsers(userList);
        existingProject.setProjectName(projectDto.getProjectName());
        existingProject.setDescription(projectDto.getDescription());
        existingProject.setStart_date(projectDto.getStart_date());
        existingProject.setEnd_date(projectDto.getEnd_date());
        existingProject.setBacklog(backlog);
        backlog.setProject(existingProject);
        backlog.setProjectIdentifier(existingProject.getProjectIdentifier().toUpperCase());

        this.projectRepository.save(existingProject);

        return Optional.of(existingProject);
    }

    @Override
    public Project findProjectByIdentifier(String projectId, String username) {
        Project project = this.projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        User user = this.userRepository.findByUsername(username);

        if (project == null) {
            throw new ProjectIdNotFoundException("Project with ID: '" + projectId + "' does not exist.");
        }

        if (!project.getProjectUsers().contains(user)) {
            throw new ProjectNotFoundException("Project not found in your account!");
        }


        return project;
    }

    @Override
    public List<Project> findAllProjects(String username) {
        User user = this.userRepository.findByUsername(username);

        return this.projectRepository.findAllByProjectUsers(user);
    }

    @Override//change
    public Set<User> getProjectUsers(Long projectId) {
        Project project = this.projectRepository.getById(projectId);
        return project.getProjectUsers();
    }

    @Override
    public void deleteProjectByIdentifier(String projectId, String username) {
        Project project = findProjectByIdentifier(projectId, username);
        Set<User> userList = project.getProjectUsers();

        for (User  u : userList) {
            u.getProjects().remove(project);
        }
        this.projectRepository.delete(project);

    }
}
