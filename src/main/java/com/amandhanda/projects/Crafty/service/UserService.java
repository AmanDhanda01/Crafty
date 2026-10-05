package com.amandhanda.projects.Crafty.service;

import com.amandhanda.projects.Crafty.dto.auth.UserProfileResponse;
import org.jspecify.annotations.Nullable;

public interface UserService {
    UserProfileResponse getProfile(Long userId);
}
