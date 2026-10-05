package com.amandhanda.projects.Crafty.dto.project;

import com.amandhanda.projects.Crafty.enums.ProjectRole;

import java.time.Instant;

public record ProjectSummaryResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt,
        ProjectRole role
) {
}
