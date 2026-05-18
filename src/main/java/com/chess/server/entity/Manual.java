package com.chess.server.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "manuals")
public class Manual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 20)
    private String dynasty;

    @Column(length = 50)
    private String author;

    @Column(length = 20)
    private String category;

    private int difficulty;

    @Column(name = "is_premium")
    private boolean premium = false;

    @Column(length = 500)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String content = "[]";

    @Column(name = "total_moves")
    private int totalMoves;

    @Column(name = "view_count")
    private int viewCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
