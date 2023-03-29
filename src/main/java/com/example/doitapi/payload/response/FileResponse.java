package com.example.doitapi.payload.response;

import com.example.doitapi.model.Task;
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
public class FileResponse {
    private Long id;
    private String name;
    private Long authorId;
    private Long taskId;
//    private byte[] content;
    private String type;
    private Date createdDate;
    private String errorMessage;
}
