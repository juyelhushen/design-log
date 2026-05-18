package com.newsletter.service;

import com.newsletter.dto.subscriber.SubscriberCreateRequest;
import com.newsletter.dto.subscriber.SubscriberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SubscriberService {

    SubscriberResponse subscribe(SubscriberCreateRequest request);
    void unsubscribe(UUID subscriberId);
    Page<SubscriberResponse> getMySubscribers(Pageable pageable);

}
