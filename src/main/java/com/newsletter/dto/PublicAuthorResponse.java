package com.newsletter.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PublicAuthorResponse(
        UUID id,
        String username,
        String name,
        String bio,
        String profileImageUrl
) {
}
