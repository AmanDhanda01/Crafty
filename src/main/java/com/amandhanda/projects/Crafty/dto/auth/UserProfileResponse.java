package com.amandhanda.projects.Crafty.dto.auth;

import lombok.Data;

@Data
public class UserProfileResponse {
    Long id;
    String name;
    String email;
    String avatarUrl;
}
