package com.example.doitapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String firstName;

    private String userInfo;
    private String lastName;
    private String email;
    private String password;
    private String oldPassword;
}