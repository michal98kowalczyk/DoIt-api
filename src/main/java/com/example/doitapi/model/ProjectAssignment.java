package com.example.doitapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_assignments")
@Data
@NoArgsConstructor
public class ProjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    private String role;
    private String accessLevel;

}
