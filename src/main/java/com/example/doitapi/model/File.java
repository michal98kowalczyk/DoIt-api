package com.example.doitapi.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;

    @ManyToOne
    @JoinColumn(name = "task_id")
    Task task;

    @Lob
    private byte[] content;

    private String type;
}
