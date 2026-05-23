package com.chess.server.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName("news")
public class News {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String category;
    private String title;
    private String summary;
    private String source;
    private String date;
    private String content;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
