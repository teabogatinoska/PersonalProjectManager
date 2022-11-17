package com.example.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, unique = true)
    private String projectSequence;

    @NotBlank(message = "Please include a Project Summary!")
    private String summary;

    private String status;
    private Integer priority;

    //ManyToOne with Backlog
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name="backlog_id",updatable = false,nullable = false)
    @JsonIgnore
    private Backlog backlog;

    //ManyToOne with User
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name="user_id",updatable = false,nullable = false)
    @JsonIgnore
    private User user;

    @Column(updatable = false)
    private String projectIdentifier;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dueDate;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    public ProjectTask() {
    }

    @PrePersist
    protected void onCreate() {
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }

    @Override
    public String toString() {
        return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", summary=" + summary
                + ", status=" + status + ", priority=" + priority
                + ", projectIdentifier=" + projectIdentifier + ", dueDate=" + dueDate + ", created_At=" + created_At
                + ", updated_At=" + updated_At + "]";
    }


}

