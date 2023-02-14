package com.example.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer PTSequence = 0;
    private String projectIdentifier;


    //OneToOne project
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;


    //OneToMany tasks
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true)
    private List<ProjectTask> projectTasks = new ArrayList<>();


    public Backlog() {
    }

}

