package com.chess.server.controller;

import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.Subscription;
import com.chess.server.entity.User;
import com.chess.server.service.SubscriptionService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscribe")
public class SubscribeController {

    private final SubscriptionService subscriptionService;

    public SubscribeController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/plans")
    public ApiResponse<List<Map<String, Object>>> plans() {
        return ApiResponse.success(subscriptionService.getPlans());
    }

    @PostMapping
    public ApiResponse<Subscription> subscribe(@RequestBody Map<String, String> body, HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        return ApiResponse.success(subscriptionService.subscribe(user.getId(), body.get("plan")));
    }

    @GetMapping("/my")
    public ApiResponse<Subscription> my(HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        return ApiResponse.success(subscriptionService.getLatest(user.getId()));
    }
}
