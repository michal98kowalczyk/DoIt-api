package com.example.doitapi.repository;

import com.example.doitapi.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
    Optional<TaskStatus> findByName(String name);
    ArrayList<TaskStatus> findAllByNameIn(ArrayList<String> names);
}
