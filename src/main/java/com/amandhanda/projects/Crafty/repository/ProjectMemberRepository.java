package com.amandhanda.projects.Crafty.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amandhanda.projects.Crafty.entity.ProjectMember;
import com.amandhanda.projects.Crafty.entity.ProjectMemberId;
import com.amandhanda.projects.Crafty.enums.ProjectRole;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember,  ProjectMemberId> {
     
    List<ProjectMember> findByIdProjectId(Long projectId);

    @Query("SELECT pm.role FROM ProjectMember pm WHERE pm.id.projectId = :projectId AND pm.id.userId = :userId")
    Optional<ProjectRole> findRoleByProjectIdAndUserId(@Param("projectId") Long projectId, @Param("userId") long userId);
}
