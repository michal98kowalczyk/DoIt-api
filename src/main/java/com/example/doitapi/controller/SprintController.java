package com.example.doitapi.controller;


import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Release;
import com.example.doitapi.model.Sprint;
import com.example.doitapi.payload.response.SprintResponse;
import com.example.doitapi.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    @GetMapping("/sprint/project/{id}")
    public ResponseEntity<ArrayList<SprintResponse>> getAllSprintByProject(@PathVariable Long id) {
        final ArrayList<SprintResponse> sprintsByProject = sprintService.getAllSprintsByProject(id);
        return ResponseEntity.ok(sprintsByProject);
    }

    @GetMapping("/sprint/project/{projectId}/release/{releaseId}")
    public ResponseEntity<ArrayList<SprintResponse>> getAllSprintByProjectAndRelease(@PathVariable Long projectId, @PathVariable Long releaseId) {
        final ArrayList<SprintResponse> sprintsByProjectAndRelease = sprintService.getAllSprintsByProjectAndRelease(projectId, releaseId);
        return ResponseEntity.ok(sprintsByProjectAndRelease);
    }

    @PostMapping("/sprint")
    public ResponseEntity<SprintResponse> addSprint(@RequestBody Sprint sprint) {
        SprintResponse saved = null;
        try {
            saved = sprintService.addSprint(sprint);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<SprintResponse>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SprintResponse.builder().errorMessage("Error occured").build());
        }
        System.out.println("project saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }
}
