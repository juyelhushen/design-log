package com.newsletter.dto.subscriber;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SubscriberResponse(

        UUID id,
        String email,
        String name,
        String source,
        boolean active,
        String createdAt

) {
}
