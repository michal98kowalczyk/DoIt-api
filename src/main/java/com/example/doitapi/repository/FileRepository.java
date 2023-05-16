package com.example.doitapi.repository;

import com.example.doitapi.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findAllByTaskId(Long id);
    Optional<File> findById(Long id);
    void deleteAllByTaskIdIn(List<Long> ids);
}
