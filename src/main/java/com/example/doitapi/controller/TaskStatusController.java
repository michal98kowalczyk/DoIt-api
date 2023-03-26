package com.example.doitapi.controller;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Task;
import com.example.doitapi.model.TaskStatus;
import com.example.doitapi.service.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class TaskStatusController {

    private final TaskStatusService taskStatusService;

    @GetMapping("/taskstatus")
    public ResponseEntity<ArrayList<TaskStatus>> getAllTaskStatuses() {
        final ArrayList<TaskStatus> taskStatuses = taskStatusService.getAllTaskStatues();
        return ResponseEntity.ok(taskStatuses);
    }

    @PostMapping("/taskstatus")
    public ResponseEntity<TaskStatus> addTaskStatus(@RequestBody TaskStatus status) {
        ArrayList<TaskStatus> taskStatus = new ArrayList<>();
        try {
            taskStatus = taskStatusService.addStatus(status);
        } catch (Exception e) {
            System.out.println("catch exception2");
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (taskStatus.isEmpty()) {
            return (ResponseEntity<TaskStatus>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TaskStatus.builder().errorMessage("Duplicate").build());
        }
        return ResponseEntity.ok(taskStatus.get(0));
    }

}
