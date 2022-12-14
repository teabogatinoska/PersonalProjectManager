package com.example.projectmanager.service;

import com.example.projectmanager.exceptions.UsernameAlreadyExistsException;
import com.example.projectmanager.model.User;
import com.example.projectmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser) {

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique
            newUser.setUsername(newUser.getUsername());
            //Don't persisting the password
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);

        }catch(Exception error) {
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists!");
        }

    }

}

