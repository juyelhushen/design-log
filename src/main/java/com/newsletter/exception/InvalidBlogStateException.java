package com.newsletter.exception;

public class InvalidBlogStateException extends RuntimeException {
    public InvalidBlogStateException(String message) {
        super(message);
    }
}
