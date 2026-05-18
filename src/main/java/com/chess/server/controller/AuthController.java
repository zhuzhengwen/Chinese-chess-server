package com.chess.server.controller;

import com.chess.server.dto.ApiResponse;
import com.chess.server.dto.LoginRequest;
import com.chess.server.dto.UserDTO;
import com.chess.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ApiResponse<UserDTO> login(@RequestBody LoginRequest req) {
        if (!req.isMockLogin() && (req.getPhone() == null || req.getPhone().isEmpty())) {
            return ApiResponse.fail(400, "手机号不能为空");
        }
        UserDTO dto = userService.login(req);
        return ApiResponse.success(dto);
    }

    @PostMapping("/register")
    public ApiResponse<UserDTO> register(@RequestBody LoginRequest req) {
        UserDTO dto = userService.login(req);
        return ApiResponse.success(dto);
    }

    @GetMapping("/profile")
    public ApiResponse<UserDTO> profile(Principal principal) {
        if (principal == null) return ApiResponse.fail(401, "未登录");
        UserDTO dto = userService.getUserByPhone(principal.getName());
        return ApiResponse.success(dto);
    }
}
