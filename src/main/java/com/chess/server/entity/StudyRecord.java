package com.chess.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

@TableName("study_records")
public class StudyRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long manualId;
    private Integer progress;
    private LocalDateTime studiedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getManualId() { return manualId; }
    public void setManualId(Long manualId) { this.manualId = manualId; }
    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
    public LocalDateTime getStudiedAt() { return studiedAt; }
    public void setStudiedAt(LocalDateTime studiedAt) { this.studiedAt = studiedAt; }
}
