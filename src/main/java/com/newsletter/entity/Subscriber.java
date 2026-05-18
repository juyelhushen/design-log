package com.newsletter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "subscribers",
        indexes = {
                @Index(name = "idx_subscriber_email", columnList = "email", unique = true),
                @Index(name = "idx_subscriber_author", columnList = "author_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscriber extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(length = 120)
    private String name;

    @Column(length = 500)
    private String source;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}