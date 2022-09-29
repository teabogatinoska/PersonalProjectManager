package com.example.projectmanager.model;

import com.example.projectmanager.model.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole roleName;

    public Role() {

    }

    public Role(ERole roleName) {
        this.roleName = roleName;
    }
}

