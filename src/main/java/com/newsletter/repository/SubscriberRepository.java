package com.newsletter.repository;

import com.newsletter.entity.Subscriber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {
    Optional<Subscriber> findByEmailIgnoreCaseAndAuthorId(String email, UUID authorId);

    Page<Subscriber> findByAuthorIdAndActiveTrue(UUID authorId, Pageable pageable);

    long countByAuthorIdAndActiveTrue(UUID authorId);
}
