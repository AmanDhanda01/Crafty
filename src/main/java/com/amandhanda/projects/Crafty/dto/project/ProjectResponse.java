package com.amandhanda.projects.Crafty.dto.project;


import java.time.Instant;

import com.amandhanda.projects.Crafty.dto.auth.UserProfileResponse;

public record ProjectResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt,
        UserProfileResponse owner
) {
}
