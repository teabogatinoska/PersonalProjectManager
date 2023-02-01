package com.example.projectmanager.repository;

import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByProjectIdentifier(String projectId);

    @Override
    List<Project> findAll();

    List<Project> findAllByProjectLeader(String username);

    List<Project> findAllByProjectUsers(User user);

}

