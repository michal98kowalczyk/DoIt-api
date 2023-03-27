package com.example.doitapi.payload.response;


import com.example.doitapi.model.AccessLevel;
import com.example.doitapi.model.Project;
import com.example.doitapi.model.User;
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
public class ProjectAssignmentResponse {
    private Long id;
    private ProjectResponse project;
    private AuthenticationResponse user;
    private String position;
    private AccessLevel accessLevel;
    private String errorMessage;
}
