package com.amandhanda.projects.Crafty.dto.project;

import com.amandhanda.projects.Crafty.dto.auth.UserProfileResponse;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt,
        UserProfileResponse owner
) {
}
