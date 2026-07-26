package com.amandhanda.projects.Crafty.security;

import org.springframework.stereotype.Component;

import com.amandhanda.projects.Crafty.enums.ProjectPermission;
import com.amandhanda.projects.Crafty.enums.ProjectRole;
import com.amandhanda.projects.Crafty.repository.ProjectMemberRepository;

import lombok.RequiredArgsConstructor;

@Component("security")
@RequiredArgsConstructor
public class SecurityExpressions {
     private final ProjectMemberRepository projectMemberRepository;
     private final AuthUtil authUtil;


     private boolean hasPermission(Long projectId, ProjectPermission permission) {
        long userId = authUtil.getCurrentUserId();

        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId, userId)
                .map(role -> role.getPermissions().contains(permission))
                .orElse(false);

        }
     public boolean canViewProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.VIEW);
     }
     public boolean canEditProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.EDIT);
     }
     public boolean canDeleteProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.DELETE);
     }
     public boolean canManageProjectMembers(Long projectId){
        return hasPermission(projectId, ProjectPermission.MANAGE_MEMBERS);
     }
     
     public boolean canViewMembers(Long projectId){
        return hasPermission(projectId, ProjectPermission.VIEW_MEMBERS);
     }
     public boolean canManageMembers(Long projectId){
        return hasPermission(projectId, ProjectPermission.MANAGE_MEMBERS);
     }

}
