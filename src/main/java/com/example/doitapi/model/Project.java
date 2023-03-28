package com.example.doitapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "project")
//    private List<ProjectAssignment> projectAssignmentList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_task_types",
            joinColumns = @JoinColumn(name = "task_type_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<TaskType> availableTaskTypes;

//    @OneToMany(mappedBy = "project")
//    private List<Task> tasks;
//
//    @OneToMany(mappedBy = "project")
//    private List<Sprint> sprints;

    private Boolean isSprintsApplicable;

//    @OneToMany(mappedBy = "project")
//    private List<Release> releases;

    @ManyToOne
    @JoinColumn(name = "owner_id")
//    @JsonBackReference
    User owner;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Transient
    private String errorMessage;
}
