package com.chess.server.controller;

import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.Manual;
import com.chess.server.entity.User;
import com.chess.server.service.ManualService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manuals")
public class ManualController {

    private final ManualService manualService;

    public ManualController(ManualService manualService) {
        this.manualService = manualService;
    }

    @GetMapping
    public ApiResponse<List<Manual>> list(@RequestParam(required = false) String category,
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
    public ApiResponse<Manual> detail(@PathVariable Long id, HttpServletRequest req) {
        Manual m = manualService.getById(id);
        if (m == null) return ApiResponse.fail(404, "棋谱不存在");
        if (Boolean.TRUE.equals(m.getPremium())) {
            User user = (User) req.getAttribute("currentUser");
            if (user == null || !Boolean.TRUE.equals(user.getVip())) {
                m.setContent(null);
            }
        }
        return ApiResponse.success(m);
    }
}
