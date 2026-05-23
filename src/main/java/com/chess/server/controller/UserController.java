package com.chess.server.controller;

import com.chess.server.dto.ApiResponse;
import com.chess.server.dto.UserDTO;
import com.chess.server.entity.User;
import com.chess.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public ApiResponse<UserDTO> profile(HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        return ApiResponse.success(UserService.toDTO(user));
    }
}
