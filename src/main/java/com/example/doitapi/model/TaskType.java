package com.example.doitapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "task_types")
@Data
@NoArgsConstructor
public class TaskType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "availableTaskTypes")
    private List<Project> projects;

    @ManyToMany
    @JoinTable(
            name = "task_type_statuses",
            joinColumns = @JoinColumn(name = "task_type_id"),
            inverseJoinColumns = @JoinColumn(name = "task_status_id"))
    private List<TaskStatus> availableTaskStatuses;

}
