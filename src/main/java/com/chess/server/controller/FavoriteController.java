package com.chess.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.Favorite;
import com.chess.server.entity.Manual;
import com.chess.server.entity.User;
import com.chess.server.mapper.FavoriteMapper;
import com.chess.server.mapper.ManualMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteMapper favoriteMapper;
    private final ManualMapper manualMapper;

    public FavoriteController(FavoriteMapper favoriteMapper, ManualMapper manualMapper) {
        this.favoriteMapper = favoriteMapper;
        this.manualMapper = manualMapper;
    }

    @GetMapping
    public ApiResponse<List<Manual>> list(HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        List<Long> ids = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("user_id", user.getId()))
                .stream().map(Favorite::getManualId).collect(Collectors.toList());
        if (ids.isEmpty()) return ApiResponse.success(new ArrayList<>());
        return ApiResponse.success(manualMapper.selectBatchIds(ids));
    }

    @PostMapping
    public ApiResponse<Favorite> add(@RequestBody Map<String, Object> body, HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        Long manualId = Long.valueOf(body.get("manualId").toString());
        Long count = favoriteMapper.selectCount(new QueryWrapper<Favorite>()
                .eq("user_id", user.getId()).eq("manual_id", manualId));
        if (count > 0) return ApiResponse.fail(400, "已收藏");
        Favorite f = new Favorite();
        f.setUserId(user.getId()); f.setManualId(manualId); f.setCreatedAt(LocalDateTime.now());
        favoriteMapper.insert(f);
        return ApiResponse.success(f);
    }

    @DeleteMapping("/{manualId}")
    public ApiResponse<Void> remove(@PathVariable Long manualId, HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        favoriteMapper.delete(new QueryWrapper<Favorite>().eq("user_id", user.getId()).eq("manual_id", manualId));
        return ApiResponse.success();
    }
}
