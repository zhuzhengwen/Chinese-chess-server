package com.chess.server.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName("players")
public class Player {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String title;
    private String era;
    private String eraClass;
    private String birthYear;
    private String bio;
    private String manuals;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getEra() { return era; }
    public void setEra(String era) { this.era = era; }
    public String getEraClass() { return eraClass; }
    public void setEraClass(String eraClass) { this.eraClass = eraClass; }
    public String getBirthYear() { return birthYear; }
    public void setBirthYear(String birthYear) { this.birthYear = birthYear; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getManuals() { return manuals; }
    public void setManuals(String manuals) { this.manuals = manuals; }
}
