package com.example.projectmanager.repository;

import com.example.projectmanager.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User getById(String id);

    //List<User> findAllByUsername(List<String> usernames);


}