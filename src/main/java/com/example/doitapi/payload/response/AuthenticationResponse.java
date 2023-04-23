package com.example.doitapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private Long userId;
    private String userEmail;
    private String role;

    private String firstName;
    private String lastName;
    private String username;
    private String info;

    private String photoUrl;
    private Boolean success = false;

    private String errorMessage;
}