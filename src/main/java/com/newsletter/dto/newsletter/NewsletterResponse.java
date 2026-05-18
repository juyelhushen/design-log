package com.newsletter.dto.newsletter;

import com.newsletter.entity.NewsletterStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record NewsletterResponse(
        UUID id,
        String subject,
        String content,
        String previewText,
        NewsletterStatus status,
        LocalDateTime scheduledAt,
        LocalDateTime sentAt,
        Integer recipientCount
) {
}
