package com.example.projectmanager.service;

import com.example.projectmanager.exceptions.UsernameAlreadyExistsException;
import com.example.projectmanager.model.User;
import com.example.projectmanager.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {




    private final UserRepository userRepository;

    private final  BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


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

    public User getUserInfo(String user_id) {
        return this.userRepository.getById(user_id);

    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

}

