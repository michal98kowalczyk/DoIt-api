package com.example.doitapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;

    @ManyToOne
    @JoinColumn(name = "task_id")
    Task task;


}
