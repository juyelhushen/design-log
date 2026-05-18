package com.newsletter.exception.custom;

public class NewsletterStateException extends RuntimeException {
    public NewsletterStateException(String message) {
        super(message);
    }
}
