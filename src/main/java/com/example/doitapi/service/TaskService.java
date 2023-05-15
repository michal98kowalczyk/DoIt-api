package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Sprint;
import com.example.doitapi.model.Task;
import com.example.doitapi.payload.response.SprintResponse;
import com.example.doitapi.payload.response.TaskResponse;
import com.example.doitapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final AuthenticationService authenticationService;

    public TaskResponse addTask(Task task) {
        final Date currentDateTime = TimeService.getCurrentDateTime();
        task.setCreatedDate(currentDateTime);
        task.setLastModifiedDate(currentDateTime);
        Task saved = null;
        try {
            saved = taskRepository.save(task);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        return getTaskResponse(task);
    }

    public ArrayList<TaskResponse> getAllTasksByProject(Long id) {
        return getTaskResponse((ArrayList<Task>) taskRepository.findAllByProjectId(id));
    }

    public ArrayList<TaskResponse> getAllTasksByProjectAndSprint(Long projectId, Long sprintId) {
        return getTaskResponse((ArrayList<Task>) taskRepository.findAllByProjectIdAndSprintId(projectId,sprintId));
    }

    public TaskResponse getTaskResponse(Task task) {
        return TaskResponse.builder().id(task.getId())
                .releaseId(task.getRelease()!=null ? task.getRelease().getId() : null)
                .projectId(task.getProject()!=null ? task.getProject().getId() : null)
                .clonedFromId(task.getClonedFrom() != null ? task.getClonedFrom().getId() : null)
                .blockedByIds(task.getBlockedBy()!=null ? getBlockedByIds(task.getBlockedBy()) : null)
                .assignee(task.getAssignee()!=null ? authenticationService.getAuthenticationResponse(task.getAssignee()) : null)
                .reporter(task.getReporter()!=null ? authenticationService.getAuthenticationResponse(task.getReporter()):null)
                .name(task.getName())
                .description(task.getDescription())
                .sprintId(task.getSprint()!=null ? task.getSprint().getId() : null)
                .type(task.getType())
                .status(task.getStatus())
                .priority(task.getPriority().name())
                .storyPoints(task.getStoryPoints())
                .createdDate(task.getCreatedDate())
                .lastModifiedDate(task.getLastModifiedDate())
                .errorMessage(task.getErrorMessage())
                .build();
    }

    private List<Long> getBlockedByIds(List<Task> blockedBy) {
        ArrayList<Long> result = new ArrayList<>();
        for (Task t: blockedBy) {
            result.add(t.getId());
        }
        return result;
    }

    public ArrayList<TaskResponse> getTaskResponse(ArrayList<Task>  tasks) {
        ArrayList<TaskResponse> taskResponses = new ArrayList<>();
        for (Task t: tasks) {
            taskResponses.add(getTaskResponse(t));
        }
        return taskResponses;
    }

}
