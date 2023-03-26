package com.example.doitapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "releases")
@Data
@NoArgsConstructor
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "release")
    private List<Sprint> sprints;

    @OneToMany(mappedBy = "release")
    private List<Task> tasks;

    @Temporal(TemporalType.DATE)
    private Date fixVersion;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Transient
    private String errorMessage;
}
