package com.chess.server.controller;

import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.Manual;
import com.chess.server.entity.User;
import com.chess.server.repository.UserRepository;
import com.chess.server.service.ManualService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/manuals")
@RequiredArgsConstructor
public class ManualController {

    private final ManualService manualService;
    private final UserRepository userRepository;

    @GetMapping
    public ApiResponse<List<Manual>> list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer difficulty) {
        return ApiResponse.success(manualService.search(category, keyword, difficulty));
    }

    @GetMapping("/recommended")
    public ApiResponse<List<Manual>> recommended() {
        return ApiResponse.success(manualService.getRecommended());
    }

    @GetMapping("/categories")
    public ApiResponse<List<Map<String, Object>>> categories() {
        return ApiResponse.success(manualService.getCategories());
    }

    @GetMapping("/{id}")
    public ApiResponse<Manual> detail(@PathVariable Long id) {
        return manualService.getById(id).map(manual -> {
            if (manual.isPremium() && !isCurrentUserVip()) {
                manual.setContent(null);
            }
            return ApiResponse.success(manual);
        }).orElse(ApiResponse.fail(404, "棋谱不存在"));
    }

    private boolean isCurrentUserVip() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return false;
        }
        Optional<User> user = userRepository.findByPhone((String) auth.getPrincipal());
        return user.map(User::isVip).orElse(false);
    }
}
