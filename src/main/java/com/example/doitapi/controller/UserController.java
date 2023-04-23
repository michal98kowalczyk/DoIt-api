package com.example.doitapi.controller;

import com.example.doitapi.payload.request.AuthenticationRequest;
import com.example.doitapi.payload.request.RegisterRequest;
import com.example.doitapi.payload.response.AuthenticationResponse;
import com.example.doitapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService service;


    @PatchMapping("/user/{id}")
    public ResponseEntity<AuthenticationResponse> updateUser(
            @RequestBody RegisterRequest request, @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(service.updateUser(request, id));
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<AuthenticationResponse> changeEmail(@RequestBody RegisterRequest request, @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(service.changeEmail(request, id));
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<AuthenticationResponse> changePassword(
            @RequestBody RegisterRequest request, @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(service.changePassword(request, id));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<AuthenticationResponse> deleteUser(
            @RequestBody RegisterRequest request, @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(service.deleteUser(request, id));
    }
}
