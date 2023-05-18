package com.example.doitapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "filters")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FilterType type;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    @ElementCollection
    List<String> filters;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
}
