package com.example.doitapi.controller;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/hello")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> sayHello() {

        try {
            return ResponseEntity.ok("Hello from secured endpoint");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok("Log in again");
        }
    }

}