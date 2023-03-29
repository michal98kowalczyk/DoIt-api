package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Project;
import com.example.doitapi.model.ProjectAssignment;
import com.example.doitapi.model.Release;
import com.example.doitapi.payload.response.ProjectResponse;
import com.example.doitapi.payload.response.ReleaseResponse;
import com.example.doitapi.repository.ReleaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReleaseService {

    private final ReleaseRepository releaseRepository;

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
                .projectId(release.getProject()!=null ? release.getProject().getId() : null)
                .createdDate(release.getCreatedDate())
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
}