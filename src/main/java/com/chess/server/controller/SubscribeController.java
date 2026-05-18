package com.chess.server.controller;

import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.Subscription;
import com.chess.server.entity.User;
import com.chess.server.repository.UserRepository;
import com.chess.server.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscribe")
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;

    @GetMapping("/plans")
    public ApiResponse<List<Map<String, Object>>> plans() {
        return ApiResponse.success(subscriptionService.getPlans());
    }

    @PostMapping
    public ApiResponse<Subscription> subscribe(@RequestBody Map<String, String> body, Principal principal) {
        Long userId = getUserId(principal);
        if (userId == null) return ApiResponse.fail(401, "未登录");
        String plan = body.get("plan");
        return ApiResponse.success(subscriptionService.subscribe(userId, plan));
    }

    @GetMapping("/my")
    public ApiResponse<Optional<Subscription>> my(Principal principal) {
        Long userId = getUserId(principal);
        if (userId == null) return ApiResponse.fail(401, "未登录");
        return ApiResponse.success(subscriptionService.getLatest(userId));
    }

    private Long getUserId(Principal principal) {
        if (principal == null) return null;
        return userRepository.findByPhone(principal.getName()).map(User::getId).orElse(null);
    }
}
