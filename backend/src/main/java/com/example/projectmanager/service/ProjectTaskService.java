package com.example.projectmanager.service;

import com.example.projectmanager.model.Backlog;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.ProjectTask;

import java.util.Optional;

public interface ProjectTaskService {

    Iterable<ProjectTask> findBacklogById(String id, String username);

    Project findProjectByBacklogId(String id, String username);

    ProjectTask findProjectTaskByProjectSequence(String backlog_id, String task_id, String username);

    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username);

    ProjectTask updateTaskByProjectSequence(ProjectTask updatedTask, String backlog_id, String task_id, String username);

    void deleteTaskByProjectSequence(String backlog_id, String task_id, String username);
}