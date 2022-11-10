package com.example.projectmanager.service.impl;

import com.example.projectmanager.exceptions.InvalidProjectStartDateException;
import com.example.projectmanager.exceptions.ProjectIdAlreadyExistsException;
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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    public Project saveOrUpdateProject(ProjectDto projectDto, String leaderUsername, List<Long> userIds) {
        if (projectDto.getId() != null) {
            Project existingProject = this.projectRepository.findByProjectIdentifier(projectDto.getProjectIdentifier());

            if (existingProject != null && (!existingProject.getProjectLeader().equals(leaderUsername))) {
                throw new ProjectNotFoundException("Project not found in your account");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with ID:'" + projectDto.getProjectIdentifier() + "' cannot be updated because it does not exist!");
            }
        }

        try {
            Project project = new Project();
            Set<User> usersSet = new HashSet<>(this.userRepository.findAllById(userIds));
            project.setUsers(usersSet);
            User projectLeader = this.userRepository.findByUsername(leaderUsername);
            project.setProjectLeader(projectLeader.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            Date currentDate = new java.util.Date();


            //saving new project
            if (projectDto.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                project.setStart_date(currentDate);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            //updating project
            if (projectDto.getId() != null) {
                projectDto.setBacklog(backlogRepository.findByProjectIdentifier(projectDto.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);

        } catch (Exception e) {
            throw new ProjectIdAlreadyExistsException("Project with ID: '" + projectDto.getProjectIdentifier().toUpperCase() + "' already exists.");
        }
    }

    @Override
    public Project findProjectByIdentifier(String projectId, String username) {
        Project project = this.projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdNotFoundException("Project with ID: '" + projectId + "' does not exist.");
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException("Project not found in your account!");
        }


        return project;
    }

    @Override
    public List<Project> findAllProjects(String username) {
        return this.projectRepository.findAllByProjectLeader(username);
    }

    @Override
    public void deleteProjectByIdentifier(String projectId, String username) {
        this.projectRepository.delete(findProjectByIdentifier(projectId, username));

    }
}
