package com.example.doitapi.controller;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Sprint;
import com.example.doitapi.model.Task;
import com.example.doitapi.model.User;
import com.example.doitapi.payload.response.SprintResponse;
import com.example.doitapi.payload.response.TaskResponse;
import com.example.doitapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        final Task t = taskService.getTaskById(id);
        return ResponseEntity.ok(t);
    }

    @GetMapping("/task/project/{id}")
    public ResponseEntity<ArrayList<TaskResponse>> getAllTasksByProject(@PathVariable Long id) {
        final ArrayList<TaskResponse> tasksByProject = taskService.getAllTasksByProject(id);
        return ResponseEntity.ok(tasksByProject);
    }

    @GetMapping("/task/assignee/{id}")
    public ResponseEntity<ArrayList<TaskResponse>> getAllTasksByAssignee(@PathVariable Long id) {
        final ArrayList<TaskResponse> tasksByAssignee = taskService.getAllTasksByAssignee(id);
        return ResponseEntity.ok(tasksByAssignee);
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

    @PatchMapping("/task")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody Task task) {
        TaskResponse saved = null;
        try {
            saved = taskService.updateTask(task);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<TaskResponse>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TaskResponse.builder().errorMessage("Error occured").build());
        }
        System.out.println("project saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/task/blocked/{id}")
    public ResponseEntity<Boolean> addBlockedBy(@PathVariable("id") String id,@RequestBody List<Long> taskIds) {
        System.out.println("tasks ids "+taskIds);
        Boolean saved = null;
        try {
            saved = taskService.addBlockedBy(Long.valueOf(id),taskIds);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<Boolean>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/task/clone/{id}")
    public ResponseEntity<Task> clone(@PathVariable("id") String id,@RequestBody User reporter) {
        Task saved = null;
        try {
            saved = taskService.clone(Long.valueOf(id), reporter);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<Task>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Task.builder().errorMessage("Error occured").build());
        }
        System.out.println("project saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }
}
