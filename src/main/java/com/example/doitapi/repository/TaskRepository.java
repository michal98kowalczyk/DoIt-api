package com.example.doitapi.repository;

import com.example.doitapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByProjectId(Long id);
    List<Task> findAllByProjectIdAndSprintId(Long projectId, Long sprintId);
}
