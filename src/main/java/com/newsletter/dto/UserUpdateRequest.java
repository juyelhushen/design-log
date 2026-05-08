package com.newsletter.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserUpdateRequest(

        @Size(max = 80, message = "Username must be at most 80 characters")
        String username,

        @Size(max = 120, message = "Name must be at most 120 characters")
        String name,

        @Size(max = 1000, message = "Bio must be at most 1000 characters")
        String bio,

        @Size(max = 500, message = "Profile image URL must be at most 500 characters")
        String profileImageUrl
) {
}
