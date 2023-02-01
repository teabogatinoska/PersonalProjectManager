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

import java.util.*;
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
    public Project saveOrUpdateProject(Project project, String leaderUsername, List<User> users) {

        if (project.getId() != null) {
            Project existingProject = this.projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if (existingProject != null && (!existingProject.getProjectLeader().equals(leaderUsername))) {
                throw new ProjectNotFoundException("Project not found in your account");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with ID:'" + project.getProjectIdentifier() + "' cannot be updated because it does not exist!");
            }
        }

        try {
            List<User> usersSet = new ArrayList<>();
            for(int i=0; i<users.size(); i++){
                usersSet.add(this.userRepository.findByUsername(users.get(i).getUsername()));
            }
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
    public Optional<Project> save(ProjectDto projectDto, String leader) {
        Project project = new Project();
        Backlog backlog = new Backlog();
        List<User> projectUsers = new ArrayList<>();
        User leaderUser = this.userRepository.findByUsername(leader);

        for(String username: projectDto.getProjectUsers()){
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

        if(projectDto.getId() != null) {
             existingProject = this.projectRepository.findByProjectIdentifier(projectDto.getProjectIdentifier());
        } else throw new ProjectNotFoundException("Project with ID:'" + projectDto.getProjectIdentifier() + "' cannot be updated because it does not exist!");

        User leaderUser = this.userRepository.findByUsername(leader);
        Backlog backlog = this.backlogRepository.findByProjectIdentifier(projectDto.getProjectIdentifier().toUpperCase());
        List<User> userList = new ArrayList<>();

        for(String username : projectDto.getProjectUsers()){
            userList.add(this.userRepository.findByUsername(username));
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

    @Override
    public List<User> getProjectUsers(Long projectId) {
        Project project = this.projectRepository.getById(projectId);
        return project.getProjectUsers();
    }

    @Override
    public void deleteProjectByIdentifier(String projectId, String username) {
        this.projectRepository.delete(findProjectByIdentifier(projectId, username));

    }
}
