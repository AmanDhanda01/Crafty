package com.amandhanda.projects.Crafty.enums;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.amandhanda.projects.Crafty.enums.ProjectPermission.*;

@RequiredArgsConstructor
@Getter
public enum ProjectRole {
    VIEWER(Set.of(VIEW,VIEW_MEMBERS)),
    EDITOR(VIEW,EDIT,DELETE,VIEW_MEMBERS),
    OWNER(Set.of(VIEW,EDIT,DELETE, MANAGE_MEMBERS,VIEW_MEMBERS));


    ProjectRole(ProjectPermission... permissions) {
        this.permissions = Set.of(permissions);
    }

    private final Set<ProjectPermission> permissions;


}
