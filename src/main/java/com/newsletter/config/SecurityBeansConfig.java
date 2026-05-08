package com.newsletter.config;


import com.newsletter.util.PemKeyUtils;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.PrivateKey;
import java.security.PublicKey;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityBeansConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PublicKey jwtPublicKey() {
        return PemKeyUtils.readPublicKey(jwtProperties.publicKey());
    }

    @Bean
    public PrivateKey jwtPrivateKey() {
        return PemKeyUtils.readPrivateKey(jwtProperties.privateKey());
    }

    @Bean
    public JwtEncoder jwtEncoder(PublicKey jwtPublicKey, PrivateKey jwtPrivateKey) {
        RSAKey rsaKey = new RSAKey.Builder((java.security.interfaces.RSAPublicKey) jwtPublicKey)
                .privateKey((java.security.interfaces.RSAPrivateKey) jwtPrivateKey)
                .build();
        JWK jwk = rsaKey;
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }


    @Bean
    public JwtDecoder jwtDecoder(PublicKey jwtPublicKey) {
        return NimbusJwtDecoder
                .withPublicKey((java.security.interfaces.RSAPublicKey) jwtPublicKey)
                .build();
    }

}
