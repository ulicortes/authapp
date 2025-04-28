package com.example.lockerAuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lockerAuth.request.LoginRequest;
import com.example.lockerAuth.request.RegisterRequest;
import com.example.lockerAuth.response.AuthResponse;
import com.example.lockerAuth.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private UserService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login_user(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(service.login(req));
    }
    @PostMapping("/register")
    public ResponseEntity<String> register_user(@RequestBody RegisterRequest req) {
        if(service.register(req)) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    
}
