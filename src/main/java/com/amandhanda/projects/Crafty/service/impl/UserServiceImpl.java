package com.amandhanda.projects.Crafty.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.auth.UserProfileResponse;
import com.amandhanda.projects.Crafty.error.ResourceNotFoundException;
import com.amandhanda.projects.Crafty.mapper.UserMapper;
import com.amandhanda.projects.Crafty.repository.UserRepository;
import com.amandhanda.projects.Crafty.security.AuthUtil;
import com.amandhanda.projects.Crafty.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor

public class UserServiceImpl implements UserService,UserDetailsService {

    UserRepository userRepository;
    UserMapper userMapper;
    AuthUtil authUtil;

    @Override
    public UserProfileResponse getProfile() {
        Long userId = authUtil.getCurrentUserId();
        return userRepository.findById(userId)
                .map(userMapper::toUserProfileResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }


}
