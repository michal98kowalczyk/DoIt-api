package com.example.doitapi.controller;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.Comment;
import com.example.doitapi.model.Task;
import com.example.doitapi.payload.response.CommentResponse;
import com.example.doitapi.payload.response.TaskResponse;
import com.example.doitapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("comment/task/{id}")
    public ResponseEntity<ArrayList<CommentResponse>> getAllCommentsByTask(@PathVariable Long id) {
        final ArrayList<CommentResponse> commentResponses = commentService.getAllCommentsByTask(id);
        return ResponseEntity.ok(commentResponses);
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> addComment(@RequestBody Comment comment) {
        CommentResponse saved = null;
        try {
            saved = commentService.addComment(comment);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<CommentResponse>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommentResponse.builder().errorMessage("Error occured").build());
        }
        System.out.println("project saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }
}
