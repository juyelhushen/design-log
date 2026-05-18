package com.newsletter.mapper;

import com.newsletter.dto.newsletter.NewsletterResponse;
import com.newsletter.entity.NewsletterCampaign;

public class NewsletterMapper {

    private NewsletterMapper() {}

    public static NewsletterResponse toResponse(NewsletterCampaign campaign) {
        return NewsletterResponse.builder()
                .id(campaign.getId())
                .subject(campaign.getSubject())
                .content(campaign.getContent())
                .previewText(campaign.getPreviewText())
                .status(campaign.getStatus())
                .scheduledAt(campaign.getScheduledAt())
                .sentAt(campaign.getSentAt())
                .recipientCount(campaign.getRecipientCount())
                .build();
    }
}
