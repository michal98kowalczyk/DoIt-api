package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.ProjectAssignment;
import com.example.doitapi.model.User;
import com.example.doitapi.payload.response.AuthenticationResponse;
import com.example.doitapi.payload.response.ProjectAssignmentResponse;
import com.example.doitapi.payload.response.ProjectResponse;
import com.example.doitapi.repository.ProjectAssignmentRepository;
import com.example.doitapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProjectAssignmentService {
    private final ProjectAssignmentRepository projectAssignmentRepository;

    private final ProjectService projectService;

    private final AuthenticationService authenticationService;



    public ArrayList<ProjectAssignment> getAllProjectAssignmentsByUser(Long id) {
        return (ArrayList<ProjectAssignment>) projectAssignmentRepository.findAllByUserId(id);
    }

    public ProjectAssignmentResponse addProjectAssignment(ProjectAssignment projectAssignment) {
        final Date currentDateTime = TimeService.getCurrentDateTime();
        projectAssignment.setCreatedDate(currentDateTime);
        projectAssignment.setLastModifiedDate(currentDateTime);
        ProjectAssignment saved = null;
        try {
            saved =  projectAssignmentRepository.save(projectAssignment);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }

        ProjectResponse projectResponse = projectService.getProjectResponse(projectService.getProject(saved.getProject().getId()));
        AuthenticationResponse authenticationResponse = authenticationService.getAuthenticationResponse(authenticationService.getUser(saved.getUser().getId()));

        return ProjectAssignmentResponse.builder().id(saved.getId()).project(projectResponse).user(authenticationResponse).accessLevel(saved.getAccessLevel()).position(saved.getPosition()).build();
    }

    public String addProjectAssignments(ArrayList<ProjectAssignment> projectAssignments) {
        final Date currentDateTime = TimeService.getCurrentDateTime();
        projectAssignments.forEach(pa -> {
            pa.setCreatedDate(currentDateTime);
            pa.setLastModifiedDate(currentDateTime);
        });
        ArrayList<ProjectAssignment> saved = null;
        try {
            saved = (ArrayList<ProjectAssignment>) projectAssignmentRepository.saveAll(projectAssignments);
        } catch (RuntimeException e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if(saved==null || saved.isEmpty()) {
            return null;
        }
        return "SUCCESS";
    }
}
