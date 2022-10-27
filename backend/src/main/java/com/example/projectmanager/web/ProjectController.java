package com.example.projectmanager.web;

import com.example.projectmanager.model.Project;
import com.example.projectmanager.service.MapValidationErrorService;
import com.example.projectmanager.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal){

        ResponseEntity<?> errorMap = this.mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) {
            return errorMap;
        }

        Project project1 = this.projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);

    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){
        Project project = this.projectService.findProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal){
        return this.projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal){
        this.projectService.deleteProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<String> ("Project with ID: '"+projectId+"' was deleted.", HttpStatus.OK);
    }


}

