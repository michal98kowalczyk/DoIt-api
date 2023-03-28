package com.example.doitapi.payload.response;

import com.example.doitapi.model.Project;
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
public class ReleaseResponse {

    private Long id;
    private Date fixVersion;
    private Long projectId;
    private Date createdDate;
    private Date lastModifiedDate;
    private String errorMessage;
}
