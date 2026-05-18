package com.newsletter.dto.newsletter;

import lombok.Builder;

@Builder
public record SendNewsletterResponse(
        String message,
        Integer totalRecipients,
        Integer queued
) {
}
