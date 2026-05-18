package com.chess.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(length = 20)
    private String plan;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    private BigDecimal price;

    @Column(length = 20)
    private String status = "ACTIVE";
}
