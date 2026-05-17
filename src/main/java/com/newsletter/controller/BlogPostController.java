package com.newsletter.controller;

import com.newsletter.dto.*;
import com.newsletter.service.BlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogService;

//    @GetMapping("/id/{id}")
//    public ResponseEntity<BlogResponse> getBlogById(@PathVariable Long id) {
//        return ResponseEntity.ok(blogService.getBlogById(id));
//    }

    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(@Valid @RequestBody BlogCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogService.createBlog(request));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<BlogResponse> getBlogBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(blogService.getBlogBySlug(slug));
    }

    @GetMapping
    public ResponseEntity<Page<BlogListItemResponse>> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return ResponseEntity.ok(blogService.getAllBlogs(pageable));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<BlogListItemResponse>> getMyBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return ResponseEntity.ok(blogService.getMyBlogs(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable Long id,
            @Valid @RequestBody BlogUpdateRequest request
    ) {
        return ResponseEntity.ok(blogService.updateBlog(id, request));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<BlogPublishResponse> publishBlog(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.publishBlog(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BlogListItemResponse>> searchBlogs(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return ResponseEntity.ok(blogService.searchBlogs(q, pageable));
    }

    private Pageable buildPageable(int page, int size, String sort) {
        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        Sort.Direction direction = sortParts.length > 1 && sortParts[1]
                .equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return PageRequest.of(page, size, Sort.by(direction, sortField));
    }
}
