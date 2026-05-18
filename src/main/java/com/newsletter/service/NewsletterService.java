package com.newsletter.service;

import com.newsletter.dto.newsletter.NewsletterCreateRequest;
import com.newsletter.dto.newsletter.NewsletterResponse;
import com.newsletter.dto.newsletter.SendNewsletterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NewsletterService {
    NewsletterResponse createDraft(NewsletterCreateRequest request);
    NewsletterResponse scheduleNewsletter(UUID newsletterId);
    SendNewsletterResponse sendNewsletterNow(UUID newsletterId);
    Page<NewsletterResponse> getMyNewsletters(Pageable pageable);
}

