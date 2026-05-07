package com.newsletter.exception.custom;

public class DuplicateBlogSlugException extends RuntimeException {
    public DuplicateBlogSlugException(String message) {
        super(message);
    }
}