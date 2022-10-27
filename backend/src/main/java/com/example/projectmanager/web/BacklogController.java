package com.example.projectmanager.web;

import com.example.projectmanager.model.ProjectTask;
import com.example.projectmanager.service.MapValidationErrorService;
import com.example.projectmanager.service.ProjectTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin("*")
public class BacklogController {

    private final ProjectTaskService projectTaskService;

    private final MapValidationErrorService mapValidationErrorService;

    public BacklogController(ProjectTaskService projectTaskService, MapValidationErrorService mapValidationErrorService) {
        this.projectTaskService = projectTaskService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTasktoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, Principal principal) {

        ResponseEntity<?> errorMap = this.mapValidationErrorService.MapValidationService(result);

        if (errorMap != null) return errorMap;

        ProjectTask newProjectTask = this.projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

        return new ResponseEntity<ProjectTask>(newProjectTask, HttpStatus.CREATED);

    }

    //Getting Backlog with Project Task by ID
    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {

        return this.projectTaskService.findBacklogById(backlog_id, principal.getName());
    }


    @GetMapping("/{backlog_id}/{task_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String task_id, Principal principal) {
        ProjectTask projectTask = this.projectTaskService.findProjectTaskByProjectSequence(backlog_id, task_id, principal.getName());
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{task_id}")
    public ResponseEntity<?> updatedProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, @PathVariable String task_id, Principal principal) {

        ResponseEntity<?> errorMap = this.mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        ProjectTask updatedTask = this.projectTaskService.updateByProjectSequence(projectTask, backlog_id, task_id, principal.getName());

        return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{task_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String task_id, Principal principal) {
        this.projectTaskService.deleteByProjectSequence(backlog_id, task_id, principal.getName());

        return new ResponseEntity<String>("Project Task with id:'" + task_id + "' was deleted successfully", HttpStatus.OK);
    }


}

