package com.amandhanda.projects.Crafty.entity;


import java.time.Instant;

import com.amandhanda.projects.Crafty.enums.ProjectRole;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "project_member")
public class ProjectMember {
   @EmbeddedId
   ProjectMemberId id;
   
   @ManyToOne
   @MapsId("projectId")
   Project project;

   @ManyToOne
   @MapsId("userId")
   User user;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   ProjectRole role;

   Instant invitedAt;
   Instant acceptedAt;
}
