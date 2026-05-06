package com.newsletter.dto;

import com.newsletter.entity.PostStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BlogResponse(
        Long id,
        String title,
        String slug,
        String summary,
        String content,
        PostStatus status,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}
