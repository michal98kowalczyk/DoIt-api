package com.example.doitapi.payload.response;

import com.example.doitapi.model.Project;
import com.example.doitapi.model.Release;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SprintResponse {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Project project;
    private Release release;
    private Date createdDate;
    private Date lastModifiedDate;

    private Integer sprintNumber;


    private Boolean isCompleted;
    private Boolean isActive;
    private String errorMessage;
}
