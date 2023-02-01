package com.example.projectmanager.web;

import com.example.projectmanager.exceptions.InvalidUserPermissionsException;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.ProjectTask;
import com.example.projectmanager.model.User;
import com.example.projectmanager.model.dto.ProjectTaskDto;
import com.example.projectmanager.service.MapValidationErrorService;
import com.example.projectmanager.service.ProjectTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
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
/*
    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTasktoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id,  Principal principal) {

        Project project = this.projectTaskService.findProjectByBacklogId(backlog_id, principal.getName());

        if (principal.getName().equals(project.getProjectLeader())) {
            ResponseEntity<?> errorMap = this.mapValidationErrorService.MapValidationService(result);

            if (errorMap != null) return errorMap;
            //System.out.println("status: " + projectTask.getStatus());
            //System.out.println("username: " + projectTask.getUser());
            ProjectTask newProjectTask = this.projectTaskService.addProjectTask(backlog_id, projectTask);

            return new ResponseEntity<ProjectTask>(newProjectTask, HttpStatus.CREATED);
        } else throw new InvalidUserPermissionsException("Only the project leader can perform this action!");
    }

 */

    @PostMapping("/{backlog_id}")
    public ResponseEntity<ProjectTask> save(@RequestBody ProjectTaskDto projectTaskDto, Principal principal, @PathVariable String backlog_id) {
        Project project = this.projectTaskService.findProjectByBacklogId(backlog_id, principal.getName());

        if (principal.getName().equals(project.getProjectLeader())) {

            return this.projectTaskService.save(projectTaskDto, backlog_id).map(projectTask -> ResponseEntity.ok().body(projectTask))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } else throw new InvalidUserPermissionsException("Only the project leader can perform this action!");
    }

    @PatchMapping("/{backlog_id}/{task_id}")
    public ResponseEntity<ProjectTask> updatedProjectTask(@RequestBody ProjectTaskDto projectTaskDto, Principal principal, @PathVariable String backlog_id, @PathVariable String task_id) {

        return this.projectTaskService.edit(projectTaskDto, backlog_id, task_id, principal.getName()).map(projectTask -> ResponseEntity.ok().body(projectTask))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {

        return this.projectTaskService.findBacklogById(backlog_id, principal.getName());
    }


    @GetMapping("/{backlog_id}/{task_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String task_id, Principal principal) {
        ProjectTask projectTask = this.projectTaskService.findProjectTaskByProjectSequence(backlog_id, task_id, principal.getName());
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @GetMapping("/user/{backlog_id}/{task_id}")
    public ResponseEntity<?> getProjectTaskUser(@PathVariable String backlog_id, @PathVariable String task_id, Principal principal) {
        User user = this.projectTaskService.findProjectTaskUser(backlog_id, task_id, principal.getName());
        System.out.println("USER: " + user.getUsername());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /*
    @PatchMapping("/{backlog_id}/{task_id}")
    public ResponseEntity<?> updatedProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, @PathVariable String task_id, Principal principal) {

        ResponseEntity<?> errorMap = this.mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        ProjectTask updatedTask = this.projectTaskService.updateTaskByProjectSequence(projectTask, backlog_id, task_id, user.getUsername());

        return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);
    }

     */

    @DeleteMapping("/{backlog_id}/{task_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String task_id, Principal principal) {

        Project project = this.projectTaskService.findProjectByBacklogId(backlog_id, principal.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // This gets the authentication
        User authUser = (User) authentication.getPrincipal(); // This gets the logged in user
        if (authUser.getUsername().equals(project.getProjectLeader())) {

            this.projectTaskService.deleteTaskByProjectSequence(backlog_id, task_id, principal.getName());

            return new ResponseEntity<String>("Project Task with id:'" + task_id + "' was deleted successfully", HttpStatus.OK);
        } else throw new InvalidUserPermissionsException("Only the project leader can perform this action!");
    }

}

