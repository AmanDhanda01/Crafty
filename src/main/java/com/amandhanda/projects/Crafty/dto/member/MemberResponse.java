package com.amandhanda.projects.Crafty.dto.member;



import java.time.Instant;

import com.amandhanda.projects.Crafty.enums.ProjectRole;

public record MemberResponse(
        Long userId,
        String username,
        String name,
        String avatarUrl,
        ProjectRole projectRole,
        Instant invitedAt
) {
}
