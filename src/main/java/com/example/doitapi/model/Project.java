package com.example.doitapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "project")
    private List<ProjectAssignment> projectAssignmentList;

    @ManyToMany
    @JoinTable(
            name = "project_task_types",
            joinColumns = @JoinColumn(name = "task_type_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<TaskType> availableTaskTypes;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @OneToMany(mappedBy = "project")
    private List<Sprint> sprints;

    private Boolean isSprintsApplicable;

    @OneToMany(mappedBy = "project")
    private List<Release> releases;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;

}
