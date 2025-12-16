package com.amandhanda.projects.Crafty.entity;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {
    private Long id;

    private String name;
    private String email;
    private String passwordHash;

    private String avatarUrl;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
