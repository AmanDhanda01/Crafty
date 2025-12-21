package com.amandhanda.projects.Crafty.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.member.InviteMemberRequest;
import com.amandhanda.projects.Crafty.dto.member.MemberResponse;
import com.amandhanda.projects.Crafty.dto.member.UpdateMemberRoleRequest;
import com.amandhanda.projects.Crafty.service.ProjectMemberService;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProjectMembers'");
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inviteMember'");
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request,
            Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMemberRole'");
    }

    @Override
    public MemberResponse deleteProjectMember(Long projectId, Long memberId, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProjectMember'");
    }

}
