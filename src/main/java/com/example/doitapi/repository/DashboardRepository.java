package com.example.doitapi.repository;

import com.example.doitapi.model.Dashboard;
import com.example.doitapi.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository  extends JpaRepository<Dashboard, Integer> {
    List<Dashboard> findAllByProjectIdIn(List<Long> ids);
}
