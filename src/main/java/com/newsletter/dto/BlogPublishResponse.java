package com.newsletter.dto;

import com.newsletter.entity.PostStatus;
import lombok.Builder;

@Builder
public record BlogPublishResponse(
        Long id,
        String slug,
        PostStatus status,
        String message
) {
}
