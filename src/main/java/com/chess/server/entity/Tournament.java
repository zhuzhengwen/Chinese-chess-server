package com.chess.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

@TableName("tournaments")
public class Tournament {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String year;
    private String location;
    private String champion;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getChampion() { return champion; }
    public void setChampion(String champion) { this.champion = champion; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
