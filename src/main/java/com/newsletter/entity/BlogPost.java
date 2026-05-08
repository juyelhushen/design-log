package com.newsletter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "blog_posts",
        indexes = {
                @Index(name = "idx_blog_slug", columnList = "slug", unique = true),
                @Index(name = "idx_blog_status", columnList = "status")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 250)
    private String slug;

    @Column(length = 500)
    private String summary;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private PostStatus status = PostStatus.DRAFT;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "blog_post_tags",
            joinColumns = @JoinColumn(name = "blog_post_id")
    )
    @Column(name = "tags", length = 50)
    @Builder.Default
    private List<String> tags = new ArrayList<>();
}