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
    private Long projectId;
    private Long assigneeId;
    private Long reporterId;
    private String type;
    private String status;
    private Long sprintId;
    private Long releaseId;
    private Long clonedFromId;
    private List<Long> blockedByIds;
    private Date createdDate;
    private Date lastModifiedDate;
    private String errorMessage;
}
