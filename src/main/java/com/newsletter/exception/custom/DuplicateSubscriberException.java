package com.newsletter.exception.custom;

public class DuplicateSubscriberException extends RuntimeException {
    public DuplicateSubscriberException(String message) {
        super(message);
    }
}
