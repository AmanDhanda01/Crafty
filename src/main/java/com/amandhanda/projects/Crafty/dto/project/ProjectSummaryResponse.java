package com.amandhanda.projects.Crafty.dto.project;

import java.time.Instant;

import com.amandhanda.projects.Crafty.enums.ProjectRole;

public record ProjectSummaryResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt,
        ProjectRole role
) {
}
