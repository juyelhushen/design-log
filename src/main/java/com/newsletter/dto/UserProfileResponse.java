package com.newsletter.dto;

import com.newsletter.entity.AuthProvider;
import com.newsletter.entity.UserRole;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserProfileResponse(
        UUID id,
        String email,
        String username,
        String name,
        String bio,
        String profileImageUrl,
        AuthProvider provider,
        UserRole role,
        boolean enabled,
        boolean emailVerified,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
