package com.amandhanda.projects.Crafty.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.amandhanda.projects.Crafty.entity.Project;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("""
            SELECT p FROM Project p
            WHERE p.deletedAt IS NULL
            AND EXISTS(
                SELECT 1 FROM ProjectMember pm
                WHERE pm.id.userId = :userId
                AND pm.id.projectId = p.id
            )
            ORDER BY p.updatedAt DESC
            """)
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId);

    @Query("""
           SELECT p FROM Project p
            WHERE p.deletedAt IS NULL
            AND p.id = :projectId
            AND EXISTS(
                SELECT 1 FROM ProjectMember pm
                WHERE pm.id.userId = :userId
                AND pm.id.projectId = p.id
            )
                """)
    Optional<Project> findAccessibleProjectById(@Param("projectId") Long projectId, @Param("userId") Long userId);
}
