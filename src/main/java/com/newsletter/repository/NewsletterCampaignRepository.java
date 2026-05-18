package com.newsletter.repository;

import com.newsletter.entity.NewsletterCampaign;
import com.newsletter.entity.NewsletterStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface NewsletterCampaignRepository extends JpaRepository<NewsletterCampaign, UUID> {

    Page<NewsletterCampaign> findByAuthorId(UUID authorId, Pageable pageable);
    Page<NewsletterCampaign> findByAuthorIdAndStatus(UUID authorId, NewsletterStatus status, Pageable pageable);

}
