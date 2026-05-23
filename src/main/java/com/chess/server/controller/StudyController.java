package com.chess.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.StudyRecord;
import com.chess.server.entity.User;
import com.chess.server.mapper.StudyRecordMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/study")
public class StudyController {

    private final StudyRecordMapper studyRecordMapper;

    public StudyController(StudyRecordMapper studyRecordMapper) {
        this.studyRecordMapper = studyRecordMapper;
    }

    @PostMapping("/record")
    public ApiResponse<StudyRecord> record(@RequestBody Map<String, Object> body, HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        Long manualId = Long.valueOf(body.get("manualId").toString());
        int progress = body.containsKey("progress") ? Integer.parseInt(body.get("progress").toString()) : 0;
        StudyRecord record = new StudyRecord();
        record.setUserId(user.getId()); record.setManualId(manualId);
        record.setProgress(progress); record.setStudiedAt(LocalDateTime.now());
        studyRecordMapper.insert(record);
        return ApiResponse.success(record);
    }

    @GetMapping("/records")
    public ApiResponse<List<StudyRecord>> records(HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        return ApiResponse.success(studyRecordMapper.selectList(new QueryWrapper<StudyRecord>().eq("user_id", user.getId())));
    }

    @GetMapping("/count")
    public ApiResponse<Long> count(HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        return ApiResponse.success(studyRecordMapper.selectCount(new QueryWrapper<StudyRecord>().eq("user_id", user.getId())));
    }
}
