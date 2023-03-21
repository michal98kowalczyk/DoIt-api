package com.example.doitapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "task_statuses")
@Data
@NoArgsConstructor
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "availableTaskStatuses")
    List<TaskType> taskTypes;
}
