package com.amandhanda.projects.Crafty.service;


import com.amandhanda.projects.Crafty.dto.member.InviteMemberRequest;
import com.amandhanda.projects.Crafty.dto.member.MemberResponse;
import com.amandhanda.projects.Crafty.dto.member.UpdateMemberRoleRequest;
import com.amandhanda.projects.Crafty.entity.ProjectMember;

import java.util.List;

public interface ProjectMemberService {
    List<MemberResponse> getProjectMembers(Long projectId, Long userId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId);

    MemberResponse deleteProjectMember(Long projectId, Long memberId, Long userId);
}
