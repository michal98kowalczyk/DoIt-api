package com.example.doitapi.repository;

import com.example.doitapi.model.Task;
import com.example.doitapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findById(Long id);
    List<Task> findAllByIdIn(List<Long> ids);

    List<Task> findAllByProjectId(Long id);

    List<Task> findAllByAssigneeId(Long id);
    List<Task> findAllByProjectIdAndSprintId(Long projectId, Long sprintId);

    void deleteByProjectId(Long projectId);
}
