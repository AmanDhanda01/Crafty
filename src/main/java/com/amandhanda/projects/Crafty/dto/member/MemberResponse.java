package com.amandhanda.projects.Crafty.dto.member;



import java.time.Instant;

import com.amandhanda.projects.Crafty.enums.ProjectRole;

public record MemberResponse(
        Long userId,
        String email,
        String name,
        String avatarUrl,
        ProjectRole role,
        Instant invitedAt
) {
}
