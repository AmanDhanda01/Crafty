package com.amandhanda.projects.Crafty.dto.member;

import com.amandhanda.projects.Crafty.enums.ProjectRole;

public record InviteMemberRequest(
        String email,
        ProjectRole role
) {
}
