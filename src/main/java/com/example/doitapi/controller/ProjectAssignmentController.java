package com.example.doitapi.controller;


import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Project;
import com.example.doitapi.model.ProjectAssignment;
import com.example.doitapi.model.User;
import com.example.doitapi.payload.response.ProjectAssignmentResponse;
import com.example.doitapi.payload.response.ProjectResponse;
import com.example.doitapi.service.ProjectAssignmentService;
import com.example.doitapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectAssignmentController {

    private final ProjectAssignmentService projectAssignmentService;

    @GetMapping("/assignment/user/{id}")
    public ResponseEntity<ArrayList<ProjectAssignment>> getAllProjectAssignments(@PathVariable Long id) {
        final ArrayList<ProjectAssignment> allProjectAssignment = projectAssignmentService.getAllProjectAssignmentsByUser(id);
        return ResponseEntity.ok(allProjectAssignment);
    }


    @PostMapping("/assignment")
    public ResponseEntity<ProjectAssignmentResponse> addProjectAssignment(@RequestBody ProjectAssignment projectAssignment) {
        ProjectAssignmentResponse saved = null;
        try {
            saved = projectAssignmentService.addProjectAssignment(projectAssignment);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<ProjectAssignmentResponse>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProjectAssignmentResponse.builder().errorMessage("Error occured").build());
        }
        System.out.println("project assig saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }
}
