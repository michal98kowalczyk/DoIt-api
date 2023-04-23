package com.example.doitapi.controller;

import com.example.doitapi.payload.request.AuthenticationRequest;
import com.example.doitapi.payload.request.RegisterRequest;
import com.example.doitapi.payload.response.AuthenticationResponse;
import com.example.doitapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

//    @CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello2() {
        System.out.println("send response from non - secured endpoint");
        return ResponseEntity.ok("Hello from non - secured endpoint");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        System.out.println("request" +request);
        return ResponseEntity.ok(service.authenticate(request));
    }


}