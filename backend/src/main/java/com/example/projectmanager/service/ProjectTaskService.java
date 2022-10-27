package com.example.projectmanager.service;

import com.example.projectmanager.model.ProjectTask;

public interface ProjectTaskService {

    Iterable<ProjectTask> findBacklogById(String id, String username);

    ProjectTask findProjectTaskByProjectSequence(String backlog_id, String task_id, String username);

    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username);

    ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String task_id, String username);

    void deleteByProjectSequence(String backlog_id, String task_id, String username);
}