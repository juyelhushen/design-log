package com.newsletter.mapper;

import com.newsletter.dto.subscriber.SubscriberResponse;
import com.newsletter.entity.Subscriber;

public class SubscriberMapper {

    private SubscriberMapper(){}

    public static SubscriberResponse toResponse(Subscriber subscriber) {
        return SubscriberResponse.builder()
                .id(subscriber.getId())
                .email(subscriber.getEmail())
                .name(subscriber.getName())
                .source(subscriber.getSource())
                .active(subscriber.isActive())
                .createdAt(subscriber.getCreatedAt() != null ? subscriber.getCreatedAt().toString() : null)
                .build();
    }
}
