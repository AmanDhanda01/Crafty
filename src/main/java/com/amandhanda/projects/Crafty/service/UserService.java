package com.amandhanda.projects.Crafty.service;

import com.amandhanda.projects.Crafty.dto.auth.UserProfileResponse;

public interface UserService {
    
    UserProfileResponse getProfile(Long userId);
}
