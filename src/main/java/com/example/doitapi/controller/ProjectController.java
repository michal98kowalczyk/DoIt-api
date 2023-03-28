package com.example.doitapi.controller;


import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Project;
import com.example.doitapi.model.TaskType;
import com.example.doitapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/project")
    public ResponseEntity<ArrayList<Project>> getAllProjects() {
        final ArrayList<Project> allProjects = projectService.getAllProjects();
        return ResponseEntity.ok(allProjects);
    }


    @PostMapping("/project")
    public ResponseEntity<Project> addTaskType(@RequestBody Project project) {
        Project saved = null;
        try {
            saved = projectService.addProject(project);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<Project>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Project.builder().errorMessage("Duplicate").build());
        }
        System.out.println("project saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }
}