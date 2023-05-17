package com.example.doitapi.repository;

import com.example.doitapi.model.ProjectAssignment;
import com.example.doitapi.model.Release;
import com.example.doitapi.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Integer> {
    List<Release> findAllByProjectId(Long id);
    void deleteByProjectId(Long projectId);

    Release findById(Long id);
}
