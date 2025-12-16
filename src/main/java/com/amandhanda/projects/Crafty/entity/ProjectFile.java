package com.amandhanda.projects.Crafty.entity;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectFile {
   Long id;

   Project project;

   String path;

   String minioObjectKey;


   User updatedBy;
   User createdBy;

   Instant createdAt;
   Instant updatedAt;
}
