package com.example.doitapi.repository;

import com.example.doitapi.model.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Integer> {

}
