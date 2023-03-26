package com.example.doitapi.controller;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.TaskStatus;
import com.example.doitapi.model.TaskType;
import com.example.doitapi.service.TaskTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class TaskTypeController {

    private final TaskTypeService taskTypeService;

    @GetMapping("/tasktype")
    public ResponseEntity<ArrayList<TaskType>> getAllTaskTypes() {
        final ArrayList<TaskType> taskStatuses = taskTypeService.getAllTaskTypes();
        return ResponseEntity.ok(taskStatuses);
    }

    @PostMapping("/tasktype")
    public ResponseEntity<TaskType> addTaskType(@RequestBody TaskType type) {
        ArrayList<TaskType> typeResult = new ArrayList<>();
        try {
            typeResult = taskTypeService.addTaskType(type);
        } catch (Exception e) {
            System.out.println("catch exception2");
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (typeResult.isEmpty()) {
            return (ResponseEntity<TaskType>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TaskType.builder().errorMessage("Duplicate").build());
        }
        return ResponseEntity.ok(typeResult.get(0));
    }
}
