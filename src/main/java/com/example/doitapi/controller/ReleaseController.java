package com.example.doitapi.controller;


import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Project;
import com.example.doitapi.model.ProjectAssignment;
import com.example.doitapi.model.Release;
import com.example.doitapi.payload.response.ProjectResponse;
import com.example.doitapi.payload.response.ReleaseResponse;
import com.example.doitapi.service.ReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReleaseController {

    private final ReleaseService releaseService;


    @GetMapping("/release/project/{id}")
    public ResponseEntity<ArrayList<ReleaseResponse>> getAllRelease(@PathVariable Long id) {
        final ArrayList<ReleaseResponse> releasesByProjectId = releaseService.getAllReleasesByProjectId(id);
        return ResponseEntity.ok(releasesByProjectId);
    }

    @PostMapping("/release")
    public ResponseEntity<ReleaseResponse> addRelease(@RequestBody Release release) {
        ReleaseResponse saved = null;
        try {
            saved = releaseService.addRelease(release);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<ReleaseResponse>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReleaseResponse.builder().errorMessage("Error occured").build());
        }
        System.out.println("project saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }
}
