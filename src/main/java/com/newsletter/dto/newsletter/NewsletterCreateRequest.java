package com.newsletter.dto.newsletter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NewsletterCreateRequest(
        @NotBlank(message = "Subject is required")
        @Size(max = 200, message = "Subject must be at most 200 characters")
        String subject,

        @NotBlank(message = "Content is required")
        String content,
        String previewText,
        LocalDateTime scheduledAt
) {
}
