package com.example.doitapi.payload.response;

import com.example.doitapi.model.TaskType;
import com.example.doitapi.model.User;
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
public class ProjectResponse {
    private Long id;
    private String name;
    private String errorMessage;

    private Boolean isSuccess;
}
