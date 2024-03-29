package com.example.doitapi.payload.response;

import com.example.doitapi.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

    private Long id;
    private String name;
    private String description;
    private Project project;
    List<String> labels;
    private AuthenticationResponse assignee;
    private AuthenticationResponse reporter;
    private String type;
    private String status;
    private String priority;
    private Sprint sprint;
    private Release release;
    private Long clonedFromId;
    private Integer storyPoints;
    private List<Long> blockedByIds;
    private Date createdDate;
    private Date lastModifiedDate;
    private String errorMessage;
}
