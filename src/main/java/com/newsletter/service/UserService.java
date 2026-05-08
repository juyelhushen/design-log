package com.newsletter.service;

import com.newsletter.dto.PublicAuthorResponse;
import com.newsletter.dto.UserProfileResponse;
import com.newsletter.dto.UserUpdateRequest;
import com.newsletter.entity.User;

import java.util.UUID;

public interface UserService {

    UserProfileResponse getMyProfile(UUID userId);

    UserProfileResponse updateMyProfile(UUID userId, UserUpdateRequest request);

    PublicAuthorResponse getPublicAuthorProfile(String username);

    User getUserEntityById(UUID userId);

}
