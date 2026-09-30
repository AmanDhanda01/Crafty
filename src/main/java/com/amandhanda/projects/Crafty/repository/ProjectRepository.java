package com.amandhanda.projects.Crafty.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amandhanda.projects.Crafty.entity.Project;
import com.amandhanda.projects.Crafty.enums.ProjectRole;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("""
        SELECT p AS project, pm.role AS role
        FROM Project p
        JOIN ProjectMember pm ON pm.project.id = p.id
        WHERE pm.user.id = :userId
          AND p.deletedAt IS NULL
            ORDER BY p.updatedAt DESC
            """)
    List<ProjectWithRole> findAllAccessibleByUser(@Param("userId") Long userId);

    @Query("""
        SELECT p AS project, pm.role AS role
        FROM Project p
        JOIN ProjectMember pm ON pm.project.id = p.id
        WHERE p.id = :projectId
          AND pm.user.id = :userId
          AND p.deletedAt IS NULL
        """)
    Optional<ProjectWithRole> findAccessibleProjectByIdWithRole(
        @Param("projectId") Long projectId,
        @Param("userId") Long userId);

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

    interface ProjectWithRole {
        Project getProject();

        ProjectRole getRole();
    }
}
