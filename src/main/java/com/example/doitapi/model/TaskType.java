package com.example.doitapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "task_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "availableTaskTypes")
    private List<Project> projects;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "task_type_statuses",
            joinColumns = @JoinColumn(name = "task_type_id"),
            inverseJoinColumns = @JoinColumn(name = "task_status_id"))
    private List<TaskStatus> availableTaskStatuses;


    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Transient
    private String errorMessage;

}
