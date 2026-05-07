package com.newsletter.exception.custom;

public class InvalidBlogStateException extends RuntimeException {
    public InvalidBlogStateException(String message) {
        super(message);
    }
}
