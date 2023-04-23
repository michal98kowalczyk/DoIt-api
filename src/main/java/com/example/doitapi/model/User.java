package com.example.doitapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String username;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    private String info;

//    @OneToMany(mappedBy = "user")
//    @OneToMany
//    private List<ProjectAssignment> projectAssigments;

//    @OneToMany(mappedBy = "assignee")
//    private List<Task> assignedTasks;
//
//    @OneToMany(mappedBy = "reporter")
//    private List<Task> reportedTasks;
//
//    @OneToMany(mappedBy = "author")
//    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "author")
//    private List<File> files;

//    @OneToMany(mappedBy = "owner")
//    @JsonManagedReference
//    private List<Project> ownedProjects;

    private Boolean isSuperUser = false;

    @Enumerated(EnumType.STRING)
    private Role role;

//    zakomentowane ze wzgledu na problemy z petla
//    @OneToMany(mappedBy = "user")
//    private List<Token> tokens;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Transient
    private String errorMessage;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email; //to provide security this method needs to be overrided to compare email with email
    }

    public String getRealUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
