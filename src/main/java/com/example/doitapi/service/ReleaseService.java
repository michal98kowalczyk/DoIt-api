package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Project;
import com.example.doitapi.model.ProjectAssignment;
import com.example.doitapi.model.Release;
import com.example.doitapi.model.Sprint;
import com.example.doitapi.payload.response.ProjectResponse;
import com.example.doitapi.payload.response.ReleaseResponse;
import com.example.doitapi.payload.response.SprintResponse;
import com.example.doitapi.repository.ReleaseRepository;
import com.example.doitapi.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseService {

    private final ReleaseRepository releaseRepository;
    private final SprintRepository sprintRepository;


    public ReleaseResponse addRelease(Release release) {
        final Date currentDateTime = TimeService.getCurrentDateTime();
        release.setCreatedDate(currentDateTime);
        release.setLastModifiedDate(currentDateTime);
        Release saved = null;
        try {
            saved = releaseRepository.save(release);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
       return getReleaseResponse(saved);
    }

    public ArrayList<ReleaseResponse> getAllReleasesByProjectId(Long id) {
        return getReleaseResponse((ArrayList<Release>) releaseRepository.findAllByProjectId(id));
    }

    public ReleaseResponse getReleaseResponse(Release release) {
        return ReleaseResponse.builder().id(release.getId())
                .fixVersion(release.getFixVersion())
                .project(release.getProject()!=null ? release.getProject() : null)
                .createdDate(release.getCreatedDate())
                .isReleased(release.getIsReleased())
                .lastModifiedDate(release.getLastModifiedDate())
                .errorMessage(release.getErrorMessage())
                .build();
    }

    public ArrayList<ReleaseResponse> getReleaseResponse(ArrayList<Release>  releases) {
        ArrayList<ReleaseResponse> releaseResponses = new ArrayList<>();
        for (Release r: releases) {
            releaseResponses.add(getReleaseResponse(r));
        }
        return releaseResponses;
    }


    public Boolean deleteReleasesFromProject(Long projectId) {

        String  errorMessage = "";
        Boolean isSuccess  = true;
        try {
            releaseRepository.deleteByProjectId(projectId);
        } catch (RuntimeException  ex ) {
            errorMessage = ex.getMessage();
            isSuccess = false;
            System.out.println(errorMessage);
        }
        return isSuccess;
    }

    public Boolean completeRelease(Long id) {
        Release fromDb = releaseRepository.findById(id);
        List<Sprint> sprints = sprintRepository.findAllByReleaseIdAndIsCompleted(id,false);
        if(!sprints.isEmpty()){
            return false;
        }

        final Date currentDateTime = TimeService.getCurrentDateTime();
        fromDb.setLastModifiedDate(currentDateTime);
        fromDb.setIsReleased(true);
        Release saved = null;
        Boolean isSuccess=true;
        try {
            saved = releaseRepository.save(fromDb);
        } catch (Exception e) {
            isSuccess=false;
            DoitApiApplication.logger.info(e.getMessage());
        }
        return isSuccess;
    }

    public ReleaseResponse updateRelease(Release release) {
        Release fromDb = releaseRepository.findById(release.getId());
        final Date currentDateTime = TimeService.getCurrentDateTime();
        fromDb.setLastModifiedDate(currentDateTime);
        fromDb.setFixVersion(release.getFixVersion());
        Release saved = null;
        try {
            saved = releaseRepository.save(fromDb);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        return getReleaseResponse(saved);
    }
}
