package com.amandhanda.projects.Crafty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amandhanda.projects.Crafty.entity.ProjectMember;
import com.amandhanda.projects.Crafty.entity.ProjectMemberId;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember,  ProjectMemberId> {
     
    List<ProjectMember> findByIdProjectId(Long projectId);
}
