package com.chess.server.dto;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String username;
    private String phone;
    private String nickname;
    private String avatar;
    private boolean vip;
    private String vipPlan;
    private LocalDateTime vipExpireDate;
    private String role;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }
    public String getVipPlan() { return vipPlan; }
    public void setVipPlan(String vipPlan) { this.vipPlan = vipPlan; }
    public LocalDateTime getVipExpireDate() { return vipExpireDate; }
    public void setVipExpireDate(LocalDateTime vipExpireDate) { this.vipExpireDate = vipExpireDate; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
