package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.TaskStatus;
import com.example.doitapi.repository.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskStatusService {


    private final TaskStatusRepository taskStatusRepository;

    public ArrayList<TaskStatus> addStatus(TaskStatus status) {
        ArrayList<TaskStatus> taskStatuses = new ArrayList<>();
        taskStatuses.add(status);
        return addStatus(taskStatuses);
    }

    public ArrayList<TaskStatus> addStatus(ArrayList<TaskStatus> statuses) {
        ArrayList<TaskStatus> taskStatuses = new ArrayList<>();
        statuses.forEach(s -> {
            taskStatuses.add(TaskStatus
                    .builder()
                    .name(s.getName())
                    .sequence(s.getSequence())
                    .createdDate(TimeService.getCurrentDateTime())
                    .lastModifiedDate(TimeService.getCurrentDateTime())
                    .build());
        });
        ArrayList<TaskStatus> savedTaskStatuses = new ArrayList<>();
        try {
            savedTaskStatuses = (ArrayList<TaskStatus>) taskStatusRepository.saveAll(taskStatuses);
        } catch (Exception e) {
            System.out.println("catch exception");
            DoitApiApplication.logger.info(e.getMessage());
        }
        return savedTaskStatuses;
    }

    public ArrayList<TaskStatus> getAllTaskStatues() {
        return (ArrayList<TaskStatus>) taskStatusRepository.findAll();
    }
}
