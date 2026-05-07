package com.newsletter.mapper;

import com.newsletter.dto.BlogListItemResponse;
import com.newsletter.dto.BlogResponse;
import com.newsletter.entity.BlogPost;

public class BlogMapper {

    public static BlogResponse toResponse(BlogPost blogPost) {
        return BlogResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .slug(blogPost.getSlug())
                .summary(blogPost.getSummary())
                .content(blogPost.getContent())
                .status(blogPost.getStatus())
                .tags(blogPost.getTags())
                .createdAt(blogPost.getCreatedAt())
                .updatedAt(blogPost.getUpdatedAt())
                .createdBy(blogPost.getCreatedBy())
                .updatedBy(blogPost.getUpdatedBy())
                .build();
    }


    public static BlogListItemResponse toListItemResponse(BlogPost blogPost) {
        return BlogListItemResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .slug(blogPost.getSlug())
                .summary(blogPost.getSummary())
                .status(blogPost.getStatus())
                .tags(blogPost.getTags())
                .createdAt(blogPost.getCreatedAt())
                .build();
    }

}
