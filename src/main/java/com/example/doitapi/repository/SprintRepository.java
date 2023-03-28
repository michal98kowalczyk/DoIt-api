package com.example.doitapi.repository;

import com.example.doitapi.model.Release;
import com.example.doitapi.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer> {
    List<Sprint> findAllByProjectId(Long id);
    List<Sprint> findAllByProjectIdAndReleaseId(Long projectId, Long releaseId);
}
