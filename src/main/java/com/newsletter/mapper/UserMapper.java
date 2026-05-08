package com.newsletter.mapper;

import com.newsletter.dto.PublicAuthorResponse;
import com.newsletter.dto.UserProfileResponse;
import com.newsletter.entity.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserProfileResponse toProfileResponse(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .name(user.getName())
                .bio(user.getBio())
                .profileImageUrl(user.getProfileImageUrl())
                .provider(user.getProvider())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .emailVerified(user.isEmailVerified())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static PublicAuthorResponse toPublicAuthorResponse(User user) {
        return PublicAuthorResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .bio(user.getBio())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
