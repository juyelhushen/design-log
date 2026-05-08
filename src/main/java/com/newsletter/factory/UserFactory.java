package com.newsletter.factory;

import com.newsletter.dto.LocalUserCreateRequest;
import com.newsletter.dto.OAuthUserCreateRequest;
import com.newsletter.entity.AuthProvider;
import com.newsletter.entity.User;
import com.newsletter.entity.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserFactory {

    public User createLocalUser(LocalUserCreateRequest request, String encodedPassword) {
        return User.builder()
                .email(request.email().trim().toLowerCase())
                .password(encodedPassword)
                .name(request.name())
                .provider(AuthProvider.LOCAL)
                .role(UserRole.USER)
                .enabled(true)
                .emailVerified(false)
                .build();
    }

    public User createOAuthUser(OAuthUserCreateRequest request, AuthProvider provider) {
        return User.builder()
                .email(request.email().trim().toLowerCase())
                .name(request.name())
                .provider(provider)
                .providerId(request.providerId())
                .profileImageUrl(request.profileImageUrl())
                .role(UserRole.USER)
                .enabled(true)
                .emailVerified(true)
                .build();
    }

    public void normalizeUsernameIfPresent(User user) {
        if (StringUtils.hasText(user.getUsername())) {
            user.setUsername(user.getUsername().trim().toLowerCase());
        }
    }

}
