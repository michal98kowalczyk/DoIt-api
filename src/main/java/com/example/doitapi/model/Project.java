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
    List<ProjectAssignment> projectAssignmentList;

    @ManyToMany
    @JoinTable(
            name = "project_task_types",
            joinColumns = @JoinColumn(name = "task_type_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    List<TaskType> availableTaskTypes;

    @OneToMany(mappedBy = "project")
    List<Task> tasks;

}
