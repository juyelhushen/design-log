package com.newsletter.exception;

public class DuplicateBlogSlugException extends RuntimeException {
    public DuplicateBlogSlugException(String message) {
        super(message);
    }
}