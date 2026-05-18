package com.chess.server.controller;

import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.StudyRecord;
import com.chess.server.entity.User;
import com.chess.server.repository.UserRepository;
import com.chess.server.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final UserRepository userRepository;

    @PostMapping("/record")
    public ApiResponse<StudyRecord> record(@RequestBody Map<String, Object> body, Principal principal) {
        Long userId = getUserId(principal);
        if (userId == null) return ApiResponse.fail(401, "未登录");
        Long manualId = Long.valueOf(body.get("manualId").toString());
        int progress = body.containsKey("progress") ? Integer.parseInt(body.get("progress").toString()) : 0;
        return ApiResponse.success(studyService.recordStudy(userId, manualId, progress));
    }

    @GetMapping("/records")
    public ApiResponse<List<StudyRecord>> records(Principal principal) {
        Long userId = getUserId(principal);
        if (userId == null) return ApiResponse.fail(401, "未登录");
        return ApiResponse.success(studyService.getUserRecords(userId));
    }

    @GetMapping("/count")
    public ApiResponse<Long> count(Principal principal) {
        Long userId = getUserId(principal);
        if (userId == null) return ApiResponse.fail(401, "未登录");
        return ApiResponse.success(studyService.getStudiedCount(userId));
    }

    private Long getUserId(Principal principal) {
        if (principal == null) return null;
        return userRepository.findByPhone(principal.getName()).map(User::getId).orElse(null);
    }
}
