package com.newsletter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record BlogCreateRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must be at most 200 characters")
        String title,

        @Size(max = 500, message = "Summary must be at most 500 characters")
        String summery,

        @NotBlank(message = "Content is required")
        String content,

        List<@Size(max = 50, message = "Each tag must be at most 50 characters") String> tags
) {
}
