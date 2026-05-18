package com.newsletter.service.impl;

import com.newsletter.dto.newsletter.NewsletterCreateRequest;
import com.newsletter.dto.newsletter.NewsletterResponse;
import com.newsletter.dto.newsletter.SendNewsletterResponse;
import com.newsletter.entity.NewsletterCampaign;
import com.newsletter.entity.NewsletterStatus;
import com.newsletter.exception.custom.NewsletterNotFoundException;
import com.newsletter.exception.custom.NewsletterStateException;
import com.newsletter.mapper.NewsletterMapper;
import com.newsletter.repository.NewsletterCampaignRepository;
import com.newsletter.repository.SubscriberRepository;
import com.newsletter.service.NewsletterService;
import com.newsletter.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsletterServiceImpl implements NewsletterService {

    private final NewsletterCampaignRepository newsletterCampaignRepository;
    private final SecurityUtils currentUserProvider;
    private final SubscriberRepository subscriberRepository;

    @Override
    public NewsletterResponse createDraft(NewsletterCreateRequest request) {
        NewsletterCampaign campaign = NewsletterCampaign.builder()
                .subject(request.subject().trim())
                .content(request.content().trim())
                .previewText(request.previewText())
                .scheduledAt(request.scheduledAt())
                .status(request.scheduledAt() != null ? NewsletterStatus.SCHEDULED : NewsletterStatus.DRAFT)
                .author(currentUserProvider.getCurrentUser())
                .build();

        return NewsletterMapper.toResponse(newsletterCampaignRepository.save(campaign));
    }

    @Override
    public NewsletterResponse scheduleNewsletter(UUID newsletterId) {
        NewsletterCampaign campaign = newsletterCampaignRepository.findById(newsletterId)
                .orElseThrow(() -> new NewsletterNotFoundException("Newsletter not found: " + newsletterId));

        if (campaign.getStatus() == NewsletterStatus.SENT) {
            throw new NewsletterStateException("Sent newsletter cannot be rescheduled");
        }

        campaign.setStatus(NewsletterStatus.SCHEDULED);
        return NewsletterMapper.toResponse(newsletterCampaignRepository.save(campaign));
    }

    @Override
    public SendNewsletterResponse sendNewsletterNow(UUID newsletterId) {
        NewsletterCampaign campaign = newsletterCampaignRepository.findById(newsletterId)
                .orElseThrow(() -> new NewsletterNotFoundException("Newsletter not found: " + newsletterId));

        if (campaign.getStatus() == NewsletterStatus.SENT) {
            throw new NewsletterStateException("Newsletter already sent");
        }

        long recipientCount = subscriberRepository.countByAuthorIdAndActiveTrue(currentUserProvider.getCurrentUser().getId());

        // later: queue email jobs here
        campaign.setStatus(NewsletterStatus.SENT);
        campaign.setSentAt(LocalDateTime.now());
        campaign.setRecipientCount((int) recipientCount);
        newsletterCampaignRepository.save(campaign);

        return SendNewsletterResponse.builder()
                .message("Newsletter sent successfully")
                .totalRecipients((int) recipientCount)
                .queued((int) recipientCount)
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public Page<NewsletterResponse> getMyNewsletters(Pageable pageable) {
        var currentUser = currentUserProvider.getCurrentUser();
        return newsletterCampaignRepository.findByAuthorId(currentUser.getId(), pageable)
                .map(NewsletterMapper::toResponse);
    }
}
