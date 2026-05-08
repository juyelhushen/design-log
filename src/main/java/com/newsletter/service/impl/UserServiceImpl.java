package com.newsletter.service.impl;

import com.newsletter.dto.PublicAuthorResponse;
import com.newsletter.dto.UserProfileResponse;
import com.newsletter.dto.UserUpdateRequest;
import com.newsletter.entity.User;
import com.newsletter.exception.custom.DuplicateUserException;
import com.newsletter.exception.custom.UserNotFoundException;
import com.newsletter.mapper.UserMapper;
import com.newsletter.repository.UserRepository;
import com.newsletter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserProfileResponse getMyProfile(UUID userId) {
        User user = getUserEntityById(userId);
        return UserMapper.toProfileResponse(user);
    }

    @Override
    public UserProfileResponse updateMyProfile(UUID userId, UserUpdateRequest request) {
        User user = getUserEntityById(userId);

        if (StringUtils.hasText(request.username())) {
            String normalizedUsername = request.username().trim().toLowerCase();
            repository.findByUsername(normalizedUsername)
                    .filter(existing -> !existing.getId().equals(userId))
                    .ifPresent(existing -> {
                        throw new DuplicateUserException("Username already exists: " + normalizedUsername);
                    });
            user.setUsername(normalizedUsername);
        }

        if (StringUtils.hasText(request.name())) {
            user.setName(request.name().trim());
        }

        if (request.bio() != null) {
            user.setBio(request.bio().trim());
        }

        if (request.profileImageUrl() != null) {
            user.setProfileImageUrl(request.profileImageUrl().trim());
        }

        User saved = repository.save(user);
        return UserMapper.toProfileResponse(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public PublicAuthorResponse getPublicAuthorProfile(String username) {
        User user = repository.findByUsername(username.trim().toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return UserMapper.toPublicAuthorResponse(user);
    }


    @Override
    @Transactional(readOnly = true)
    public User getUserEntityById(UUID userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }
}
