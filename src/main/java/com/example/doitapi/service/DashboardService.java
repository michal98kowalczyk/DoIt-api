package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Comment;
import com.example.doitapi.model.Dashboard;
import com.example.doitapi.model.ProjectAssignment;
import com.example.doitapi.model.User;
import com.example.doitapi.payload.response.CommentResponse;
import com.example.doitapi.repository.DashboardRepository;
import com.example.doitapi.repository.ProjectAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;
    private final ProjectAssignmentRepository projectAssignmentRepository;

    public Dashboard addDashboard(Dashboard dashboard) {
        final Date currentDateTime = TimeService.getCurrentDateTime();
        dashboard.setCreatedDate(currentDateTime);
        dashboard.setLastModifiedDate(currentDateTime);
        Dashboard saved = null;
        try {
            saved = dashboardRepository.save(dashboard);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        return saved;
    }

    public List<Dashboard> getMyDashboards(Long userId) {
        final List<ProjectAssignment> allByUserId = projectAssignmentRepository.findAllByUserId(userId);
        List<Long> projectIds = allByUserId.stream().map(pa->pa.getProject().getId()).collect(Collectors.toList());
        System.out.println("projectIds "+projectIds);
        return dashboardRepository.findAllByProjectIdIn(projectIds);
    }

    public Dashboard findById(Long dId) {
        return dashboardRepository.findById(dId).get();
    }


}
