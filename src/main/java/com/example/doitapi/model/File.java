package com.example.doitapi.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private User author;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Lob
    private byte[] content;

    private String type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Transient
    private String errorMessage;
}
