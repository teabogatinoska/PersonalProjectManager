package com.example.projectmanager.service.impl;

import com.example.projectmanager.exceptions.InvalidProjectStartDateException;
import com.example.projectmanager.exceptions.ProjectIdAlreadyExistsException;
import com.example.projectmanager.exceptions.ProjectIdNotFoundException;
import com.example.projectmanager.exceptions.ProjectNotFoundException;
import com.example.projectmanager.model.Backlog;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.User;
import com.example.projectmanager.repository.BacklogRepository;
import com.example.projectmanager.repository.ProjectRepository;
import com.example.projectmanager.repository.UserRepository;
import com.example.projectmanager.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    public Project saveOrUpdateProject(Project project, String leaderUsername, Set<User> users) {
        if (project.getId() != null) {
            Project existingProject = this.projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if (existingProject != null && (!existingProject.getProjectLeader().equals(leaderUsername))) {
                throw new ProjectNotFoundException("Project not found in your account");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with ID:'" + project.getProjectIdentifier() + "' cannot be updated because it does not exist!");
            }
        }

        try {
            List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());
            Set<User> usersSet = new HashSet<>(this.userRepository.findAllById(userIds));
            project.setProjectUsers(usersSet);
            User projectLeader = this.userRepository.findByUsername(leaderUsername);
            project.setProjectLeader(projectLeader.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            Date currentDate = new java.util.Date();


            //saving new project
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                project.setStart_date(currentDate);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            //updating project
            if (project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);

        } catch (Exception e) {
            throw new ProjectIdAlreadyExistsException("Project with ID: '" + project.getProjectIdentifier().toUpperCase() + "' already exists.");
        }
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

    @Override
    public void deleteProjectByIdentifier(String projectId, String username) {
        this.projectRepository.delete(findProjectByIdentifier(projectId, username));

    }
}
