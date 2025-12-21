package com.amandhanda.projects.Crafty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amandhanda.projects.Crafty.dto.auth.AuthResponse;
import com.amandhanda.projects.Crafty.dto.auth.LoginRequest;
import com.amandhanda.projects.Crafty.dto.auth.SignUpRequest;
import com.amandhanda.projects.Crafty.dto.auth.UserProfileResponse;
import com.amandhanda.projects.Crafty.service.AuthService;
import com.amandhanda.projects.Crafty.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private UserService userService;
    

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile() {
        long userId = 1L;
        return ResponseEntity.ok(userService.getProfile(userId));
    }

}
