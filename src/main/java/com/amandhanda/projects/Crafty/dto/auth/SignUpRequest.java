package com.amandhanda.projects.Crafty.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(@Size(min=1, max=30) String name, @Email @NotBlank String username, @Size(min = 4) String password) {

}
