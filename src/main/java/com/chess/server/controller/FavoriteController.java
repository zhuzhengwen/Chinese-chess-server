package com.chess.server.controller;

import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.Favorite;
import com.chess.server.entity.Manual;
import com.chess.server.entity.User;
import com.chess.server.repository.FavoriteRepository;
import com.chess.server.repository.ManualRepository;
import com.chess.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final ManualRepository manualRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ApiResponse<List<Manual>> list(Principal principal) {
        Long userId = getUserId(principal);
        if (userId == null) return ApiResponse.fail(401, "未登录");
        List<Long> ids = favoriteRepository.findByUserId(userId).stream()
                .map(Favorite::getManualId).collect(Collectors.toList());
        return ApiResponse.success(manualRepository.findAllById(ids));
    }

    @PostMapping
    public ApiResponse<Favorite> add(@RequestBody Map<String, Object> body, Principal principal) {
        Long userId = getUserId(principal);
        if (userId == null) return ApiResponse.fail(401, "未登录");
        Long manualId = Long.valueOf(body.get("manualId").toString());
        if (favoriteRepository.existsByUserIdAndManualId(userId, manualId)) {
            return ApiResponse.fail(400, "已收藏");
        }
        Favorite f = new Favorite();
        f.setUserId(userId);
        f.setManualId(manualId);
        return ApiResponse.success(favoriteRepository.save(f));
    }

    @DeleteMapping("/{manualId}")
    public ApiResponse<Void> remove(@PathVariable Long manualId, Principal principal) {
        Long userId = getUserId(principal);
        if (userId == null) return ApiResponse.fail(401, "未登录");
        favoriteRepository.deleteByUserIdAndManualId(userId, manualId);
        return ApiResponse.success();
    }

    private Long getUserId(Principal principal) {
        if (principal == null) return null;
        return userRepository.findByPhone(principal.getName()).map(User::getId).orElse(null);
    }
}
