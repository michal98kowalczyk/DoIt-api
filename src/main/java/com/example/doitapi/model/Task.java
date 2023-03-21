package com.example.doitapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    User assignee;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    User reporter;

    String type;

    String status;

    @OneToMany(mappedBy = "task")
    List<Comment> comments;

    @OneToMany(mappedBy = "task")
    List<File> files;
}
