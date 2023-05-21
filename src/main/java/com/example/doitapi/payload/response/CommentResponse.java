package com.example.doitapi.payload.response;

import com.example.doitapi.model.Comment;
import com.example.doitapi.model.Task;
import com.example.doitapi.model.User;
import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String body;
    private User author;
    private Long taskId;
    private Long parentId;
    private Date createdDate;
    private Date lastModifiedDate;
    private String errorMessage;

}
