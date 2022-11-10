package com.example.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Project Identifier is required")
    @Size(min = 4, max = 5, message = "Please use 4 or 5 characteres")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;

    @NotBlank(message = "Project description is required")
    private String description;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Future(message = "Project start date should not be in the past")
    private Date start_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Future(message = "Task End date should not be in the past")
    private Date end_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Future(message = "Project start date should not be in the past")
    @Column(updatable = false)
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore // It won't show this backlog (with all project tasks) object when search a project by id
    private Backlog backlog;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users;

    private String projectLeader;


    public Project() {
    }


    @PrePersist
    protected void onCreate() {
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }


}

