package com.chess.server.controller;

import com.chess.server.dto.*;
import com.chess.server.entity.User;
import com.chess.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest req) {
        try {
            return ApiResponse.success(userService.login(req));
        } catch (RuntimeException e) {
            return ApiResponse.fail(400, e.getMessage());
        }
    }

    @PostMapping("/register")
    public ApiResponse<UserDTO> register(@RequestBody LoginRequest req) {
        try {
            return ApiResponse.success(userService.register(req));
        } catch (RuntimeException e) {
            return ApiResponse.fail(400, e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@RequestBody LoginRequest req) {
        return ApiResponse.success();
    }

    @GetMapping("/profile")
    public ApiResponse<UserDTO> profile(HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        return ApiResponse.success(UserService.toDTO(user));
    }
}
