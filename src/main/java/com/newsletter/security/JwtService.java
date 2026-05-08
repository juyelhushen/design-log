package com.newsletter.security;

import com.newsletter.config.JwtProperties;
import com.newsletter.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;

    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(jwtProperties.accessTokenTtl());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.issuer())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(user.getEmail())
                .claim("uid", user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("roles", List.of("ROLE_" + user.getRole().name()))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
