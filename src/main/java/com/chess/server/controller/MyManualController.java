package com.chess.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.MyManual;
import com.chess.server.entity.User;
import com.chess.server.mapper.MyManualMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/my-manuals")
public class MyManualController {

    private final MyManualMapper myManualMapper;

    public MyManualController(MyManualMapper myManualMapper) {
        this.myManualMapper = myManualMapper;
    }

    @GetMapping
    public ApiResponse<List<MyManual>> list(HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        return ApiResponse.success(myManualMapper.selectList(
                new QueryWrapper<MyManual>().eq("user_id", user.getId()).orderByDesc("created_at")));
    }

    @PostMapping
    public ApiResponse<MyManual> create(@RequestBody Map<String, Object> body, HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        MyManual m = new MyManual();
        m.setUserId(user.getId());
        m.setTitle(str(body.get("title")));
        m.setType(str(body.getOrDefault("type", "custom")));
        m.setDifficulty(body.containsKey("difficulty") ? Integer.parseInt(str(body.get("difficulty"))) : 3);
        m.setRedPlayer(str(body.get("redPlayer")));
        m.setBlackPlayer(str(body.get("blackPlayer")));
        m.setGameDate(str(body.get("gameDate")));
        m.setMoves(str(body.get("moves")));
        m.setRemark(str(body.get("remark")));
        m.setCreatedAt(LocalDateTime.now());
        myManualMapper.insert(m);
        return ApiResponse.success(m);
    }

    @PutMapping("/{id}")
    public ApiResponse<MyManual> update(@PathVariable Long id, @RequestBody Map<String, Object> body, HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        MyManual m = myManualMapper.selectOne(
                new QueryWrapper<MyManual>().eq("id", id).eq("user_id", user.getId()));
        if (m == null) return ApiResponse.fail(404, "不存在");
        if (body.containsKey("title")) m.setTitle(str(body.get("title")));
        if (body.containsKey("type")) m.setType(str(body.get("type")));
        if (body.containsKey("difficulty")) m.setDifficulty(Integer.parseInt(str(body.get("difficulty"))));
        if (body.containsKey("redPlayer")) m.setRedPlayer(str(body.get("redPlayer")));
        if (body.containsKey("blackPlayer")) m.setBlackPlayer(str(body.get("blackPlayer")));
        if (body.containsKey("gameDate")) m.setGameDate(str(body.get("gameDate")));
        if (body.containsKey("moves")) m.setMoves(str(body.get("moves")));
        if (body.containsKey("remark")) m.setRemark(str(body.get("remark")));
        myManualMapper.updateById(m);
        return ApiResponse.success(m);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        if (user == null) return ApiResponse.fail(401, "未登录");
        myManualMapper.delete(new QueryWrapper<MyManual>().eq("id", id).eq("user_id", user.getId()));
        return ApiResponse.success();
    }

    private String str(Object o) {
        return o == null ? null : o.toString();
    }
}
