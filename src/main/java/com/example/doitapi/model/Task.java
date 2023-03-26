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

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

    @OneToMany(mappedBy = "task")
    private List<File> files;

    @Temporal(TemporalType.DATE)
    private Date fixVersion;

    @OneToOne
    @JoinColumn(name = "sprint_id", referencedColumnName = "id")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "release_id")
    private Release release;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Transient
    private String errorMessage;
}
