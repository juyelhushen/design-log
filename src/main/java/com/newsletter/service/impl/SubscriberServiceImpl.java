package com.newsletter.service.impl;

import com.newsletter.dto.subscriber.SubscriberCreateRequest;
import com.newsletter.dto.subscriber.SubscriberResponse;
import com.newsletter.entity.Subscriber;
import com.newsletter.exception.custom.DuplicateSubscriberException;
import com.newsletter.exception.custom.SubscriberNotFoundException;
import com.newsletter.mapper.SubscriberMapper;
import com.newsletter.repository.SubscriberRepository;
import com.newsletter.service.SubscriberService;
import com.newsletter.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SecurityUtils securityUtils;

    @Override
    public SubscriberResponse subscribe(SubscriberCreateRequest request) {
        var currentUser = securityUtils.getCurrentUser();
        subscriberRepository.findByEmailIgnoreCaseAndAuthorId(request.email(), currentUser.getId())
                .ifPresent(existing -> {
                    throw new DuplicateSubscriberException("Subscriber already exists for this author: " + request.email());
                });

        Subscriber subscriber = Subscriber.builder()
                .email(request.email().trim().toLowerCase())
                .name(request.name())
                .source(request.source())
                .active(true)
                .author(currentUser)
                .build();

        return SubscriberMapper.toResponse(subscriberRepository.save(subscriber));
    }

    @Override
    public void unsubscribe(UUID subscriberId) {
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new SubscriberNotFoundException("Subscriber not found: " + subscriberId));
        subscriber.setActive(false);
        subscriberRepository.save(subscriber);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubscriberResponse> getMySubscribers(Pageable pageable) {
        var currentUser = securityUtils.getCurrentUser();
        return subscriberRepository.findByAuthorIdAndActiveTrue(currentUser.getId(), pageable)
                .map(SubscriberMapper::toResponse);
    }
}
