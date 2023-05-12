package com.example.doitapi.controller;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Sprint;
import com.example.doitapi.model.Task;
import com.example.doitapi.payload.response.SprintResponse;
import com.example.doitapi.payload.response.TaskResponse;
import com.example.doitapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/task/project/{id}")
    public ResponseEntity<ArrayList<TaskResponse>> getAllTasksByProject(@PathVariable Long id) {
        final ArrayList<TaskResponse> tasksByProject = taskService.getAllTasksByProject(id);
        return ResponseEntity.ok(tasksByProject);
    }

    @GetMapping("/task/project/{projectId}/sprint/{sprintId}")
    public ResponseEntity<ArrayList<TaskResponse>> getAllTasksByProjectAndSprint(@PathVariable Long projectId, @PathVariable Long sprintId) {
        final ArrayList<TaskResponse> tasksByProjectAndRelease = taskService.getAllTasksByProjectAndSprint(projectId, sprintId);
        return ResponseEntity.ok(tasksByProjectAndRelease);
    }

    @PostMapping("/task")
    public ResponseEntity<TaskResponse> addTask(@RequestBody Task task) {
        TaskResponse saved = null;
        try {
            saved = taskService.addTask(task);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<TaskResponse>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TaskResponse.builder().errorMessage("Error occured").build());
        }
        System.out.println("project saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }
}
