package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Sprint;
import com.example.doitapi.model.Task;
import com.example.doitapi.model.User;
import com.example.doitapi.payload.response.SprintResponse;
import com.example.doitapi.payload.response.TaskResponse;
import com.example.doitapi.repository.FileRepository;
import com.example.doitapi.repository.TaskRepository;
import com.example.doitapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

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

    public TaskResponse updateTask(Task task) {
        Task fromDb = taskRepository.findById(task.getId()).get();

        User assignee = null;
        if(task.getAssignee() != null){
            assignee = userRepository.findById(task.getAssignee().getId()).get();
        }
        final Date currentDateTime = TimeService.getCurrentDateTime();
        fromDb.setLastModifiedDate(currentDateTime);

        fromDb.setName(task.getName());
        fromDb.setDescription(task.getDescription());
        fromDb.setStatus(task.getStatus());
        fromDb.setPriority(task.getPriority());
        fromDb.setLabels(task.getLabels());
        fromDb.setStoryPoints(task.getStoryPoints());
        fromDb.setAssignee(assignee);
        fromDb.setDueDate(task.getDueDate());
        fromDb.setSprint(task.getSprint());

        Task saved = null;
        try {
            saved = taskRepository.save(fromDb);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        return getTaskResponse(saved);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }
    public ArrayList<TaskResponse> getAllTasksByProject(Long id) {
        return getTaskResponse((ArrayList<Task>) taskRepository.findAllByProjectId(id));
    }

    public ArrayList<TaskResponse> getAllTasksByAssignee(Long id) {
        return getTaskResponse((ArrayList<Task>) taskRepository.findAllByAssigneeId(id));
    }

    public ArrayList<TaskResponse> getAllTasksByProjectAndSprint(Long projectId, Long sprintId) {
        return getTaskResponse((ArrayList<Task>) taskRepository.findAllByProjectIdAndSprintId(projectId,sprintId));
    }

    public TaskResponse getTaskResponse(Task task) {
        return TaskResponse.builder().id(task.getId())
                .release(task.getRelease()!=null ? task.getRelease(): null)
                .project(task.getProject()!=null ? task.getProject() : null)
                .clonedFromId(task.getClonedFrom() != null ? task.getClonedFrom().getId() : null)
                .blockedByIds(task.getBlockedBy()!=null ? getBlockedByIds(task.getBlockedBy()) : null)
                .assignee(task.getAssignee()!=null ? authenticationService.getAuthenticationResponse(task.getAssignee()) : null)
                .reporter(task.getReporter()!=null ? authenticationService.getAuthenticationResponse(task.getReporter()):null)
                .name(task.getName())
                .labels(task.getLabels())
                .description(task.getDescription())
                .sprint(task.getSprint()!=null ? task.getSprint() : null)
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

    public Boolean deleteTasksFromProject(Long projectId) {
        ArrayList<TaskResponse> tasks = getAllTasksByProject(projectId);
        List<Long> ids =  tasks.stream().map(t->t.getId()).collect(Collectors.toList());
        System.out.println("tasks id to delete "+ids);
        String  errorMessage = "";
        Boolean isSuccess  = true;
        try {
            fileRepository.deleteAllByTaskIdIn(ids);
            taskRepository.deleteByProjectId(projectId);
        } catch (RuntimeException  ex ) {
            errorMessage = ex.getMessage();
            isSuccess = false;
            System.out.println(errorMessage);
        }
        return isSuccess;
    }

    public Boolean addBlockedBy(Long valueOf, List<Long> taskIds) {
        Task fromDb = taskRepository.findById(valueOf).get();

        List<Task> blockers = taskRepository.findAllByIdIn(taskIds);
        System.out.println("blockers "+blockers);
        fromDb.setBlockedBy(blockers);
        final Date currentDateTime = TimeService.getCurrentDateTime();
        fromDb.setLastModifiedDate(currentDateTime);
        Task saved = null;
        Boolean isSuccess = true;
        try {
            saved = taskRepository.save(fromDb);
        } catch (Exception e) {
            isSuccess = false;
            DoitApiApplication.logger.info(e.getMessage());
        }
        return isSuccess;
    }

    public Task clone(Long clonedId, User reporter) {
        User reporterFromDb = userRepository.findById(reporter.getId()).get();
        Task fromDb = taskRepository.findById(clonedId).get();
        Task newTask = Task.builder().build();
        newTask.setReporter(reporterFromDb);
        newTask.setClonedFrom(fromDb);
        newTask.setAssignee(fromDb.getAssignee());
        newTask.setName(fromDb.getName());
        newTask.setDescription(fromDb.getDescription());
        newTask.setProject(fromDb.getProject());
        newTask.setPriority(fromDb.getPriority());
        newTask.setSprint(fromDb.getSprint());
        newTask.setRelease(fromDb.getRelease());
        newTask.setType(fromDb.getType());
        newTask.setStatus(fromDb.getStatus());
        newTask.setStoryPoints(fromDb.getStoryPoints());
        newTask.setDueDate(fromDb.getDueDate());

        final Date currentDateTime = TimeService.getCurrentDateTime();
        newTask.setCreatedDate(currentDateTime);
        newTask.setLastModifiedDate(currentDateTime);
        Task saved = null;
        Boolean isSuccess = true;
        try {
            saved = taskRepository.save(newTask);
        } catch (Exception e) {
            isSuccess = false;
            DoitApiApplication.logger.info(e.getMessage());
        }
        return saved;
    }
}
