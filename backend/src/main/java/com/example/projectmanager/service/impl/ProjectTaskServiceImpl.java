package com.example.projectmanager.service.impl;

import com.example.projectmanager.exceptions.ProjectNotFoundException;
import com.example.projectmanager.model.Backlog;
import com.example.projectmanager.model.ProjectTask;
import com.example.projectmanager.repository.BacklogRepository;
import com.example.projectmanager.repository.ProjectRepository;
import com.example.projectmanager.repository.ProjectTaskRepository;
import com.example.projectmanager.service.ProjectTaskService;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private final BacklogRepository backlogRepository;

    private final ProjectTaskRepository projectTaskRepository;

    private final ProjectRepository projectRepository;

    private final ProjectServiceImpl projectService;

    public ProjectTaskServiceImpl(BacklogRepository backlogRepository, ProjectTaskRepository projectTaskRepository, ProjectRepository projectRepository, ProjectServiceImpl projectService) {
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }


    @Override
    public Iterable<ProjectTask> findBacklogById(String id, String username) {
        this.projectService.findProjectByIdentifier(id, username);

        return this.projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    @Override
    public ProjectTask findProjectTaskByProjectSequence(String backlog_id, String task_id, String username) {
        this.projectService.findProjectByIdentifier(backlog_id, username);
        ProjectTask projectTask = this.projectTaskRepository.findByProjectSequence(task_id);

        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task with id: '" + task_id + "' was not found!");
        }

        //make sure that the backlog/project id in the path corresponds to the right project
        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task '" + task_id + "' does not exist on the following Project: '" + backlog_id + "'");
        }
        return projectTask;
    }

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        Backlog backlog = this.projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
        projectTask.setBacklog(backlog);

        Integer BacklogSequence = backlog.getPTSequence();
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);

        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //default priority 3
        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }
        //default status TO-DO
        if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }
        return this.projectTaskRepository.save(projectTask);
    }

    @Override
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String task_id, String username) {
        ProjectTask projectTask = this.findProjectTaskByProjectSequence(backlog_id, task_id, username);
        projectTask = updatedTask;

        return this.projectTaskRepository.save(projectTask);
    }

    @Override
    public void deleteByProjectSequence(String backlog_id, String task_id, String username) {
        ProjectTask projectTask = this.findProjectTaskByProjectSequence(backlog_id, task_id, username);
        this.projectTaskRepository.delete(projectTask);
    }
}
