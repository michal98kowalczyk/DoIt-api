package com.example.doitapi.service;


import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.TaskType;
import com.example.doitapi.model.TaskType;
import com.example.doitapi.repository.TaskTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskTypeService {

    private final TaskTypeRepository taskTypeRepository;

    public TaskType findByName(String taskName) {
        final Optional<TaskType> byName = taskTypeRepository.findByName(taskName);
        if (!byName.isPresent()) {
            return null;
        }
        return byName.get();
    }
    public ArrayList<TaskType> getAllTaskTypes() {
        return (ArrayList<TaskType>) taskTypeRepository.findAll();
    }

    public ArrayList<TaskType> addTaskType(TaskType tt) {
        ArrayList<TaskType> taskTypes = new ArrayList<>();
        taskTypes.add(tt);
        return addTaskType(taskTypes);
    }

    public ArrayList<TaskType> addTaskType(ArrayList<TaskType> taskTypes) {
        final Date currentDateTime = TimeService.getCurrentDateTime();
        taskTypes.forEach(tt -> {
            tt.setCreatedDate(currentDateTime);
            tt.setLastModifiedDate(currentDateTime);
        });
        ArrayList<TaskType> savedTaskTypes = new ArrayList<>();
        try {
            savedTaskTypes = (ArrayList<TaskType>) taskTypeRepository.saveAll(taskTypes);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        return savedTaskTypes;
    }


}
