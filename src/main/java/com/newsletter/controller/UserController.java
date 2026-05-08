package com.newsletter.controller;

import com.newsletter.dto.PublicAuthorResponse;
import com.newsletter.dto.UserProfileResponse;
import com.newsletter.dto.UserUpdateRequest;
import com.newsletter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(@RequestParam UUID userId) {
        return ResponseEntity.ok(userService.getMyProfile(userId));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            @RequestParam UUID userId,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.ok(userService.updateMyProfile(userId, request));
    }

    @GetMapping("/{username}")
    public ResponseEntity<PublicAuthorResponse> getPublicAuthorProfile(@PathVariable String username) {
        return ResponseEntity.ok(userService.getPublicAuthorProfile(username));
    }
}
