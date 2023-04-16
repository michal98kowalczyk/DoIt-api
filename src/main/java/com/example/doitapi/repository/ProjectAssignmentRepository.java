package com.example.doitapi.repository;

import com.example.doitapi.model.ProjectAssignment;
import com.example.doitapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Integer> {
    List<ProjectAssignment> findAllByUserId(Long id);
}
