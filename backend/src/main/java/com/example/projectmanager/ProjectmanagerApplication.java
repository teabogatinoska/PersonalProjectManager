package com.example.projectmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class ProjectmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectmanagerApplication.class, args);
    }

}
