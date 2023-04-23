package com.example.doitapi.controller;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/hello")
public class DemoController {

//    @CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*", exposedHeaders = {"Access-Control-Allow-Origin"}, allowCredentials = "true")
    @GetMapping
    public ResponseEntity<String> sayHello() {
        System.out.println("send response secured endpoint");

        try {

            return ResponseEntity.ok("Hello from secured endpoint");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok("Log in again");
        }
    }

}