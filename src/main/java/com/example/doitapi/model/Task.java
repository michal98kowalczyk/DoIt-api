package com.example.doitapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;

    private String type;

    private String status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    Integer storyPoints;

//    @OneToMany(mappedBy = "task")
//    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "task")
//    private List<File> files;


    @ManyToOne
    @JoinColumn(name = "sprint_id", referencedColumnName = "id")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "release_id")
    private Release release;

    @OneToOne
    @JoinColumn(name = "clonedFrom_id", referencedColumnName = "id")
    private Task clonedFrom;

    @OneToMany
    private List<Task> blockedBy;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Transient
    private String errorMessage;
}
