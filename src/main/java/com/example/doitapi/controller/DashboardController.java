package com.example.doitapi.controller;


import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Comment;
import com.example.doitapi.model.Dashboard;
import com.example.doitapi.payload.response.CommentResponse;
import com.example.doitapi.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @PostMapping("/dashboard")
    public ResponseEntity<Dashboard> addDashboard(@RequestBody Dashboard dashboard) {
        Dashboard saved = null;
        try {
            saved = dashboardService.addDashboard(dashboard);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<Dashboard>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Dashboard.builder().errorMessage("Error occured").build());
        }
        System.out.println("project saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/dashboard/{userId}")
    public ResponseEntity<List<Dashboard>> getMyDashboards(@PathVariable("userId") String userId) {
        List<Dashboard> result = null;
        try {
            result = dashboardService.getMyDashboards(Long.valueOf(userId));
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }
}
