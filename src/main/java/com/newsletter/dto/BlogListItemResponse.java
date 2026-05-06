package com.newsletter.dto;

import com.newsletter.entity.PostStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BlogListItemResponse(
        Long id,
        String title,
        String slug,
        String summary,
        PostStatus status,
        List<String> tags,
        LocalDateTime createdAt
) {
}
