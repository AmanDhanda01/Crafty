package com.amandhanda.projects.Crafty.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.member.InviteMemberRequest;
import com.amandhanda.projects.Crafty.dto.member.MemberResponse;
import com.amandhanda.projects.Crafty.dto.member.UpdateMemberRoleRequest;
import com.amandhanda.projects.Crafty.entity.Project;
import com.amandhanda.projects.Crafty.entity.ProjectMember;
import com.amandhanda.projects.Crafty.entity.ProjectMemberId;
import com.amandhanda.projects.Crafty.entity.User;
import com.amandhanda.projects.Crafty.mapper.ProjectMemberMapper;
import com.amandhanda.projects.Crafty.repository.ProjectMemberRepository;
import com.amandhanda.projects.Crafty.repository.ProjectRespository;
import com.amandhanda.projects.Crafty.repository.UserRepository;
import com.amandhanda.projects.Crafty.service.ProjectMemberService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectMemberRepository projectMemberRepository;
    ProjectRespository projectRespository;
    UserRepository userRepository;
    ProjectMemberMapper projectMemberMapper;

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {

        Project project = getAccessibleProjectById(projectId, userId);

        List<MemberResponse> members = new ArrayList<>();

        members.add(projectMemberMapper.toMemberResponseFromOwner(project.getOwner()));

        members.addAll(projectMemberRepository.findByIdProjectId(projectId).stream()
                .map(projectMemberMapper::toProjectMemberResponseFromMember).toList());

        return members;
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        
         Project project = getAccessibleProjectById(projectId, userId);

         if(!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Only the owner can invite members");
         }

         User invitee = userRepository.findByEmail(request.email()).orElseThrow();

         if(invitee.getId().equals(userId)) {
            throw new RuntimeException("You cannot invite yourself");
         }

         ProjectMemberId projectMemberId = new ProjectMemberId(invitee.getId(),projectId);

         if(projectMemberRepository.existsById(projectMemberId)) {
             throw new RuntimeException("User is already a member of the project");
         }
        
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .role(request.role())
                .invitedAt(Instant.now())
                .build();
        
        projectMember = projectMemberRepository.save(projectMember);

        return projectMemberMapper.toProjectMemberResponseFromMember(projectMember);
        
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request,
            Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

         if(!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Only the owner can update member roles");
         }

         ProjectMemberId projectMemberId = new ProjectMemberId(memberId, projectId);
         ProjectMember projectMember = projectMemberRepository.findById(projectMemberId).orElseThrow();
         projectMember.setRole(request.role());

         return projectMemberMapper.toProjectMemberResponseFromMember(projectMemberRepository.save(projectMember));
    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId, Long userId) {
         Project project = getAccessibleProjectById(projectId, userId);

         if(!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Only the owner can delete member");
         }
        ProjectMemberId projectMemberId = new ProjectMemberId(memberId, projectId);

        if(!projectMemberRepository.existsById(projectMemberId)) {
            throw new RuntimeException("User is not a member of the project");
        }
        
        projectMemberRepository.deleteById(projectMemberId);
       
    }

    private Project getAccessibleProjectById(Long id, Long userId) {
        return projectRespository.findAccessibleProjectById(id, userId).orElseThrow();
    }

}
