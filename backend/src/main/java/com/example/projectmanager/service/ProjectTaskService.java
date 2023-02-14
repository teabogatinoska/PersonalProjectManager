package com.example.projectmanager.service;

import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.ProjectTask;
import com.example.projectmanager.model.User;
import com.example.projectmanager.model.dto.ProjectTaskDto;

import java.util.Optional;

public interface ProjectTaskService {

    Iterable<ProjectTask> findBacklogById(String id, String username);

    Project findProjectByBacklogId(String id, String username);

    ProjectTask findProjectTaskByProjectSequence(String backlog_id, String task_id, String username);

    Optional<ProjectTask> save (ProjectTaskDto projectTaskDto, String projectIdentifier);

    Optional<ProjectTask> edit (ProjectTaskDto projectTaskDto, String backlog_id, String task_id, String username);

    void deleteTaskByProjectSequence(String backlog_id, String task_id, String username);

    /*User findProjectTaskUser(String backlog_id, String task_id, String username);*/
}