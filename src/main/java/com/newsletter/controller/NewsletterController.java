package com.newsletter.controller;

import com.newsletter.dto.newsletter.NewsletterCreateRequest;
import com.newsletter.dto.newsletter.NewsletterResponse;
import com.newsletter.dto.newsletter.SendNewsletterResponse;
import com.newsletter.service.NewsletterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/newsletters")
@RequiredArgsConstructor
public class NewsletterController {

    private final NewsletterService newsletterService;

    @PostMapping
    public ResponseEntity<NewsletterResponse> createDraft(@Valid @RequestBody NewsletterCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsletterService.createDraft(request));
    }

    @PatchMapping("/{id}/schedule")
    public ResponseEntity<NewsletterResponse> schedule(@PathVariable UUID id) {
        return ResponseEntity.ok(newsletterService.scheduleNewsletter(id));
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<SendNewsletterResponse> sendNow(@PathVariable UUID id) {
        return ResponseEntity.ok(newsletterService.sendNewsletterNow(id));
    }

    @GetMapping
    public ResponseEntity<Page<NewsletterResponse>> getMyNewsletters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return ResponseEntity.ok(newsletterService.getMyNewsletters(pageable));
    }

    private Pageable buildPageable(int page, int size, String sort) {
        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return PageRequest.of(page, size, Sort.by(direction, sortField));
    }

}
