package com.newsletter.service.impl;

import com.newsletter.dto.*;
import com.newsletter.entity.BlogPost;
import com.newsletter.entity.PostStatus;
import com.newsletter.exception.custom.BlogNotFoundException;
import com.newsletter.exception.custom.InvalidBlogStateException;
import com.newsletter.mapper.BlogMapper;
import com.newsletter.repository.BlogPostRepository;
import com.newsletter.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    private static final Pattern DUPLICATE_DASHES = Pattern.compile("-+");

    private final BlogPostRepository blogRepository;

    @Override
    public BlogResponse createBlog(BlogCreateRequest request) {
        String slug = generateUniqueSlug(request.title());
        BlogPost blogPost = BlogPost.builder()
                .title(request.title().trim())
                .slug(slug)
                .summary(request.summery() != null ? request.summery().trim() : null)
                .content(request.content().trim())
                .status(PostStatus.DRAFT)
                .tags(safeTags(request.tags()))
                .build();

        BlogPost saved = blogRepository.save(blogPost);
        return BlogMapper.toResponse(saved);
    }

    @Override
    public BlogResponse getBlogBySlug(String slug) {
        BlogPost blogPost = blogRepository.findBySlug(slug)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found for slug: " + slug));
        return BlogMapper.toResponse(blogPost);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlogListItemResponse> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable)
                .map(BlogMapper::toListItemResponse);
    }

    @Override
    public BlogResponse updateBlog(Long id, BlogUpdateRequest request) {
        BlogPost blogPost = getBlogOrThrow(id);

        if (blogPost.getStatus() == PostStatus.PUBLISHED) {
            throw new InvalidBlogStateException("Published blog cannot be edited. Create a new draft or archive first.");
        }

        blogPost.setTitle(request.title().trim());
        blogPost.setSummary(request.summary() != null ? request.summary().trim() : null);
        blogPost.setContent(request.content().trim());
        blogPost.setTags(safeTags(request.tags()));

        // keep slug stable on update to avoid broken links
        BlogPost saved = blogRepository.save(blogPost);
        return BlogMapper.toResponse(saved);
    }

    @Override
    public BlogPublishResponse publishBlog(Long id) {
        BlogPost blogPost = getBlogOrThrow(id);

        if (blogPost.getStatus() == PostStatus.PUBLISHED) {
            throw new InvalidBlogStateException("Blog is already published.");
        }

        blogPost.setStatus(PostStatus.PUBLISHED);
        BlogPost saved = blogRepository.save(blogPost);

        return BlogPublishResponse.builder()
                .id(saved.getId())
                .slug(saved.getSlug())
                .status(saved.getStatus())
                .message("Blog published successfully")
                .build();
    }

    @Override
    public void deleteBlog(Long id) {
        BlogPost blogPost = getBlogOrThrow(id);
        blogRepository.delete(blogPost);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlogListItemResponse> searchBlogs(String query, Pageable pageable) {
        if (!StringUtils.hasText(query)) {
            return blogRepository.findAll(pageable).map(BlogMapper::toListItemResponse);
        }
        return blogRepository
                .findByTitleContainingIgnoreCaseOrSummaryContainingIgnoreCaseOrContentContainingIgnoreCase(
                        query, query, query, pageable)
                .map(BlogMapper::toListItemResponse);
    }

    private BlogPost getBlogOrThrow(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found for id: " + id));
    }


    private String generateUniqueSlug(String title) {
        String baseSlug = slugify(title);
        String candidate = baseSlug;
        int counter = 1;

        while (blogRepository.existsBySlug(candidate)) {
            candidate = baseSlug + "-" + counter++;
        }
        return candidate;
    }

    private List<String> safeTags(List<String> tags) {
        if (tags == null) {
            return new ArrayList<>();
        }
        return tags.stream()
                .filter(StringUtils::hasText)
                .map(String::trim)
                .distinct()
                .toList();
    }


    private String slugify(String input) {
        if (!StringUtils.hasText(input)) {
            throw new IllegalArgumentException("Title must not be blank");
        }

        String nowhitespace = WHITESPACE.matcher(input.trim()).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        slug = slug.toLowerCase(Locale.ENGLISH);
        slug = DUPLICATE_DASHES.matcher(slug).replaceAll("-");
        slug = slug.replaceAll("^-|-$", "");

        if (!StringUtils.hasText(slug)) {
            throw new IllegalArgumentException("Unable to generate slug from title");
        }
        return slug;
    }

}
