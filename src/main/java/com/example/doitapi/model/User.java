package com.example.doitapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    @Column(unique=true)
    private String email;

    @NonNull
    private String password;

    @OneToMany(mappedBy = "user")
    List<ProjectAssignment> projectAssigments;

    @OneToMany(mappedBy = "assignee")
    List<Task> assignedTasks;

    @OneToMany(mappedBy = "reporter")
    List<Task> reportedTasks;

    @OneToMany(mappedBy = "author")
    List<Comment> comments;

    @OneToMany(mappedBy = "author")
    List<File> files;
}
