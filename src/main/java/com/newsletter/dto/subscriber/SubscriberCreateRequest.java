package com.newsletter.dto.subscriber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SubscriberCreateRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @Size(max = 120, message = "Name must be at most 120 characters")
        String name,

        @Size(max = 500, message = "Source must be at most 500 characters")
        String source
) {
}
