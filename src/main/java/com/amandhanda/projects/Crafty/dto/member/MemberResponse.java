package com.amandhanda.projects.Crafty.dto.member;

import com.amandhanda.projects.Crafty.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String username,
        String name,
        ProjectRole role,
        Instant invitedAt
) {
}
