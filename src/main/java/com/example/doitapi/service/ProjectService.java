package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Project;
import com.example.doitapi.model.TaskStatus;
import com.example.doitapi.model.TaskType;
import com.example.doitapi.payload.response.ProjectResponse;
import com.example.doitapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

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
        try {
            saved = projectRepository.save(project);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        return ProjectResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .build();
    }

    public ProjectResponse getProjectResponse(Project project) {
        return ProjectResponse.builder().id(project.getId()).name(project.getName()).errorMessage(project.getErrorMessage()).build();
    }
}
