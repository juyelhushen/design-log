package com.newsletter.service;

import com.newsletter.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogPostService {

    BlogResponse createBlog(BlogCreateRequest request);

    BlogResponse getBlogBySlug(String slug);

    Page<BlogListItemResponse> getMyBlogs(Pageable pageable);

    Page<BlogListItemResponse> getAllBlogs(Pageable pageable);

    BlogResponse updateBlog(Long id, BlogUpdateRequest request);

    BlogPublishResponse publishBlog(Long id);

    void deleteBlog(Long id);

    Page<BlogListItemResponse> searchBlogs(String query, Pageable pageable);

}
