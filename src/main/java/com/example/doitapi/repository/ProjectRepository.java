package com.example.doitapi.repository;

import com.example.doitapi.model.Project;
import com.example.doitapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findByName(String name);
    Optional<Project> findByOwner(User owner);

}
