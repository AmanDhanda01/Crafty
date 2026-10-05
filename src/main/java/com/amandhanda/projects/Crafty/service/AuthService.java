package com.amandhanda.projects.Crafty.service;

import com.amandhanda.projects.Crafty.dto.auth.AuthResponse;
import com.amandhanda.projects.Crafty.dto.auth.LoginRequest;
import com.amandhanda.projects.Crafty.dto.auth.SignupRequest;
import org.jspecify.annotations.Nullable;

public interface AuthService {
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
