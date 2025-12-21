package com.amandhanda.projects.Crafty.service.impl;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.auth.AuthResponse;
import com.amandhanda.projects.Crafty.dto.auth.LoginRequest;
import com.amandhanda.projects.Crafty.dto.auth.SignUpRequest;
import com.amandhanda.projects.Crafty.service.AuthService;


@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public AuthResponse signup(SignUpRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'signup'");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
   
}
