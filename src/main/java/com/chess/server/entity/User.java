package com.chess.server.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String phone;

    @Column(length = 50)
    private String nickname;

    @Column(name = "password_hash")
    private String passwordHash;

    private String avatar = "";

    @Column(name = "is_vip")
    private boolean vip = false;

    @Column(name = "vip_plan", length = 20)
    private String vipPlan;

    @Column(name = "vip_expire_date")
    private LocalDateTime vipExpireDate;

    @Column(length = 20)
    private String role = "USER";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
