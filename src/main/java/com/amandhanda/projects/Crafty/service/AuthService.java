package com.amandhanda.projects.Crafty.service;

import org.jspecify.annotations.Nullable;

import com.amandhanda.projects.Crafty.dto.auth.AuthResponse;
import com.amandhanda.projects.Crafty.dto.auth.LoginRequest;
import com.amandhanda.projects.Crafty.dto.auth.SignUpRequest;

public interface AuthService {

    AuthResponse signup(SignUpRequest request);
    AuthResponse login(LoginRequest request);

}
