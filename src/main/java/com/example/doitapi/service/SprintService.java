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
                .releaseId(sprint.getRelease() !=null ? sprint.getRelease().getId() : null)
                .projectId(sprint.getProject() != null ? sprint.getProject().getId() : null)
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .sprintNumber(sprint.getSprintNumber())
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
}
