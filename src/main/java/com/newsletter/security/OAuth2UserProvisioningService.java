package com.newsletter.security;

import com.newsletter.entity.AuthProvider;
import com.newsletter.entity.User;
import com.newsletter.entity.UserRole;
import com.newsletter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class OAuth2UserProvisioningService {

    private final UserRepository userRepository;

    public User upsertGoogleUser(OAuth2User oauth2User) {
        String email = safeString(oauth2User.getAttribute("email")).toLowerCase();
        String name = safeString(oauth2User.getAttribute("name"));
        String providerId = safeString(oauth2User.getAttribute("sub"));
        String picture = safeString(oauth2User.getAttribute("picture"));

        return userRepository.findByProviderAndProviderId(AuthProvider.GOOGLE, providerId)
                .map(existing -> {
                    existing.setEmail(email);
                    existing.setName(name);
                    existing.setProfileImageUrl(picture);
                    existing.setEnabled(true);
                    return userRepository.save(existing);
                })
                .orElseGet(() -> userRepository.findByEmail(email)
                        .map(existing -> {
                            existing.setProvider(AuthProvider.GOOGLE);
                            existing.setProviderId(providerId);
                            existing.setName(name);
                            existing.setProfileImageUrl(picture);
                            existing.setEnabled(true);
                            existing.setEmailVerified(true);
                            return userRepository.save(existing);
                        })
                        .orElseGet(() -> userRepository.save(User.builder()
                                .email(email)
                                .name(name)
                                .username(generateUsername(email, name))
                                .provider(AuthProvider.GOOGLE)
                                .providerId(providerId)
                                .profileImageUrl(picture)
                                .role(UserRole.USER)
                                .enabled(true)
                                .emailVerified(true)
                                .build())));
    }

    private String safeString(Object value) {
        return value == null ? "" : String.valueOf(value).trim();
    }

    private String generateUsername(String email, String name) {
        String base = StringUtils.hasText(name)
                ? name.toLowerCase().replaceAll("[^a-z0-9]+", ".")
                : email.substring(0, email.indexOf('@')).toLowerCase();
        return base.replaceAll("^\\.|\\.$", "");
    }
}
