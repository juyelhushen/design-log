package com.newsletter.exception.custom;

public class NewsletterNotFoundException extends RuntimeException {
    public NewsletterNotFoundException(String message) {
        super(message);
    }
}
