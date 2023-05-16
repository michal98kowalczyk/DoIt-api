package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Release;
import com.example.doitapi.model.Sprint;
import com.example.doitapi.payload.response.ReleaseResponse;
import com.example.doitapi.payload.response.SprintResponse;
import com.example.doitapi.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;

    public SprintResponse addSprint(Sprint sprint) {
        final Date currentDateTime = TimeService.getCurrentDateTime();
        sprint.setCreatedDate(currentDateTime);
        sprint.setLastModifiedDate(currentDateTime);
        Sprint saved = null;
        ArrayList<Sprint> orderedBySprintNumber = (ArrayList<Sprint>) sprintRepository.findAllByProjectIdOrderBySprintNumber(sprint.getProject().getId());
        if (!orderedBySprintNumber.isEmpty()) {
            Sprint last = orderedBySprintNumber.get(orderedBySprintNumber.size()-1);
            sprint.setSprintNumber(last.getSprintNumber()+1);
        } else {
            sprint.setSprintNumber(1);
        }

        try {
            saved = sprintRepository.save(sprint);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        return getSprintResponse(saved);
    }

    public ArrayList<SprintResponse> getAllSprintsByProject(Long id) {
        return getSprintResponse((ArrayList<Sprint>) sprintRepository.findAllByProjectId(id));
    }

    public ArrayList<SprintResponse> getAllSprintsByProjectAndRelease(Long projectId, Long releaseId) {
        return getSprintResponse((ArrayList<Sprint>) sprintRepository.findAllByProjectIdAndReleaseId(projectId,releaseId));
    }

    public SprintResponse getSprintResponse(Sprint sprint) {
        return SprintResponse.builder().id(sprint.getId())
                .release(sprint.getRelease() !=null ? sprint.getRelease() : null)
                .project(sprint.getProject() != null ? sprint.getProject() : null)
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .sprintNumber(sprint.getSprintNumber())
                .isCompleted(sprint.getIsCompleted())
                .isActive(sprint.getIsActive())
                .createdDate(sprint.getCreatedDate())
                .lastModifiedDate(sprint.getLastModifiedDate())
                .errorMessage(sprint.getErrorMessage())
                .build();
    }

    public ArrayList<SprintResponse> getSprintResponse(ArrayList<Sprint>  sprints) {
        ArrayList<SprintResponse> sprintResponses = new ArrayList<>();
        for (Sprint s: sprints) {
            sprintResponses.add(getSprintResponse(s));
        }
        return sprintResponses;
    }

    public Boolean deleteSprintFromProject(Long projectId) {
        String  errorMessage = "";
        Boolean isSuccess  = true;
        try {
            sprintRepository.deleteByProjectId(projectId);
        } catch (RuntimeException  ex ) {
            errorMessage = ex.getMessage();
            isSuccess = false;
            System.out.println(errorMessage);
        }
        return isSuccess;
    }

    public SprintResponse updateSprint(Sprint sprint) {
        Sprint fromDb = sprintRepository.findById(sprint.getId());
        final Date currentDateTime = TimeService.getCurrentDateTime();
        fromDb.setLastModifiedDate(currentDateTime);
        fromDb.setEndDate(sprint.getEndDate());
        fromDb.setStartDate(sprint.getStartDate());
        fromDb.setRelease(sprint.getRelease());
        Sprint saved = null;
        try {
            saved = sprintRepository.save(fromDb);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        return getSprintResponse(saved);
    }

    public Boolean startSprint(Long id) {
        Sprint fromDb = sprintRepository.findById(id);
        final Date currentDateTime = TimeService.getCurrentDateTime();
        fromDb.setLastModifiedDate(currentDateTime);
        fromDb.setIsActive(true);
        Sprint saved = null;
        Boolean isSuccess=true;
        try {
            saved = sprintRepository.save(fromDb);
        } catch (Exception e) {
            isSuccess=false;
            DoitApiApplication.logger.info(e.getMessage());
        }
        return isSuccess;
    }

    public Boolean completeSprint(Long id) {
        Sprint fromDb = sprintRepository.findById(id);
        final Date currentDateTime = TimeService.getCurrentDateTime();
        fromDb.setLastModifiedDate(currentDateTime);
        fromDb.setIsCompleted(true);
        fromDb.setIsActive(false);
        Sprint saved = null;
        Boolean isSuccess=true;
        try {
            saved = sprintRepository.save(fromDb);
        } catch (Exception e) {
            isSuccess=false;
            DoitApiApplication.logger.info(e.getMessage());
        }
        return isSuccess;
    }
}
