package com.chess.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "study_records")
public class StudyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "manual_id", nullable = false)
    private Long manualId;

    private int progress = 0;

    @Column(name = "studied_at")
    private LocalDateTime studiedAt = LocalDateTime.now();
}
