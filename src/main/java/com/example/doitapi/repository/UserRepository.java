package com.example.doitapi.repository;

import java.util.List;
import java.util.Optional;

import com.example.doitapi.model.Role;
import com.example.doitapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Role role);
}
