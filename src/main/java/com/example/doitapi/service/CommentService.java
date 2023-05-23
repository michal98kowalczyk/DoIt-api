package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Comment;
import com.example.doitapi.model.Task;
import com.example.doitapi.model.User;
import com.example.doitapi.payload.response.CommentResponse;
import com.example.doitapi.payload.response.TaskResponse;
import com.example.doitapi.repository.CommentRepository;
import com.example.doitapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentResponse addComment(Comment comment) {
        final Date currentDateTime = TimeService.getCurrentDateTime();
        User author = userRepository.findById(comment.getAuthor().getId()).get();
        comment.setAuthor(author);
        comment.setCreatedDate(currentDateTime);
        comment.setLastModifiedDate(currentDateTime);
        Comment saved = null;
        try {
            saved = commentRepository.save(comment);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        return getCommentResponse(comment);
    }

    public ArrayList<CommentResponse> getAllCommentsByTask(Long id) {
        return getCommentResponse((ArrayList<Comment>) commentRepository.findAllByTaskId(id));
    }


    public CommentResponse getCommentResponse(Comment comment) {
        return CommentResponse.builder().id(comment.getId())
                .body(comment.getBody())
                .taskId(comment.getTask()!=null ? comment.getTask().getId() : null)
                .parentId(comment.getParent()!=null ? comment.getParent().getId() : null)
                .author(comment.getAuthor()!=null ? comment.getAuthor() : null)
                .createdDate(comment.getCreatedDate())
                .lastModifiedDate(comment.getLastModifiedDate())
                .errorMessage(comment.getErrorMessage())
                .build();
    }


    public ArrayList<CommentResponse> getCommentResponse(ArrayList<Comment>  comments) {
        ArrayList<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment c: comments) {
            commentResponses.add(getCommentResponse(c));
        }
        return commentResponses;
    }

}
