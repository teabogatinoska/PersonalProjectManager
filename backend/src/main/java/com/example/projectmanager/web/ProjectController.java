package com.example.projectmanager.web;

import com.example.projectmanager.exceptions.InvalidUserPermissionsException;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.User;
import com.example.projectmanager.model.dto.ProjectDto;
import com.example.projectmanager.service.MapValidationErrorService;
import com.example.projectmanager.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/project")
@CrossOrigin("*")
public class ProjectController {


    private final ProjectService projectService;


    private final MapValidationErrorService mapValidationErrorService;

    public ProjectController(ProjectService projectService, MapValidationErrorService mapValidationErrorService) {
        this.projectService = projectService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody ProjectDto projectDto, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = this.mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        } else {
            System.out.println("Project Dto: " + projectDto.toString());
            return this.projectService.save(projectDto, principal.getName()).map(project -> ResponseEntity.ok().body(project))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }

    }

    @PatchMapping("/updateProject/{projectId}")
    public ResponseEntity<Project> updateProject(@Valid @RequestBody ProjectDto projectDto, @PathVariable String projectId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = (User) authentication.getPrincipal();

        if (authUser.getUsername().equals(projectDto.getProjectLeader())) {
            return this.projectService.edit(projectDto, authUser.getUsername()).map(project -> ResponseEntity.ok().body(project))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } else throw new InvalidUserPermissionsException("Only the project leader can perform this action!");
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {
        Project project = this.projectService.findProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal) {
        return this.projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal) {

        Project project = this.projectService.findProjectByIdentifier(projectId, principal.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = (User) authentication.getPrincipal();

        if (authUser.getUsername().equals(project.getProjectLeader())) {
            this.projectService.deleteProjectByIdentifier(projectId, principal.getName());
            return new ResponseEntity<String>("Project with ID: '" + projectId + "' was deleted.", HttpStatus.OK);
        } else throw new InvalidUserPermissionsException("Only the project leader can perform this action!");
    }

    @GetMapping("/projectUsers/{project_id}")
    public Iterable<?> getProjectUsers(@PathVariable Long project_id) {
        return this.projectService.getProjectUsers(project_id);
    }

}

