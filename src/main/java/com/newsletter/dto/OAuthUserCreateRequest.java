package com.newsletter.dto;

import lombok.Builder;

@Builder
public record OAuthUserCreateRequest(
        String email,
        String name,
        String providerId,
        String profileImageUrl
) {
}
