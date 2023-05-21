package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Project;
import com.example.doitapi.model.TaskStatus;
import com.example.doitapi.model.TaskType;
import com.example.doitapi.model.User;
import com.example.doitapi.payload.response.ProjectResponse;
import com.example.doitapi.repository.ProjectAssignmentRepository;
import com.example.doitapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ReleaseService releaseService;
    private final ProjectAssignmentRepository projectAssignmentRepository;
    private final TaskService taskService;


    private final SprintService sprintService;
    public Project getProject(Long id) {
        return  projectRepository.findById(id).get();
    }


    public ArrayList<Project> getAllProjects() {
        return (ArrayList<Project>) projectRepository.findAll();
    }

    public ProjectResponse addProject(Project project) {
        final Date currentDateTime = TimeService.getCurrentDateTime();
        project.setCreatedDate(currentDateTime);
        project.setLastModifiedDate(currentDateTime);
        Project saved = null;

        Boolean isSuccess = true;
        String errorMsg="";
        try {
            saved = projectRepository.save(project);
        } catch (Exception e) {
            isSuccess = false;
            errorMsg = e.getMessage();
            DoitApiApplication.logger.info(e.getMessage());
        }
        return ProjectResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .isSuccess(isSuccess)
                .errorMessage(errorMsg)
                .build();
    }

    public ProjectResponse getProjectResponse(Project project, String errorMessage, Boolean isSuccess) {
        return ProjectResponse.builder().id(project.getId()).name(project.getName()).errorMessage(errorMessage).isSuccess(isSuccess).build();
    }
    @Transactional
    public ProjectResponse deleteProject(String id) {
        Project projectFromDB =  getProject(Long.valueOf(id));
        String  errorMessage = "";
        Boolean isSuccess  = true;
        try {
            sprintService.deleteSprintFromProject(projectFromDB.getId());
            releaseService.deleteReleasesFromProject(projectFromDB.getId());
            projectAssignmentRepository.deleteByProjectId(projectFromDB.getId());
            taskService.deleteTasksFromProject(projectFromDB.getId());
            projectRepository.delete(projectFromDB);
        } catch (RuntimeException  ex ) {
            errorMessage = ex.getMessage();
            isSuccess = false;
            System.out.println(errorMessage);
        }
        return getProjectResponse(projectFromDB,errorMessage,isSuccess);
    }
}
