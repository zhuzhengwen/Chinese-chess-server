package com.chess.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.*;
import com.chess.server.mapper.*;
import com.chess.server.service.ManualService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ManualService manualService;
    private final ManualMapper manualMapper;
    private final TournamentMapper tournamentMapper;
    private final TournamentGameMapper tournamentGameMapper;
    private final PlayerMapper playerMapper;
    private final NewsMapper newsMapper;

    public AdminController(ManualService manualService, ManualMapper manualMapper,
                           TournamentMapper tournamentMapper, TournamentGameMapper tournamentGameMapper,
                           PlayerMapper playerMapper, NewsMapper newsMapper) {
        this.manualService = manualService;
        this.manualMapper = manualMapper;
        this.tournamentMapper = tournamentMapper;
        this.tournamentGameMapper = tournamentGameMapper;
        this.playerMapper = playerMapper;
        this.newsMapper = newsMapper;
    }

    // ── 棋谱 ──────────────────────────────────────────────────────────────
    @GetMapping("/manuals")
    public ApiResponse<List<Manual>> manualList(@RequestParam(required = false) String keyword, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        return ApiResponse.success(manualService.search(null, keyword, null));
    }

    @PostMapping("/manuals")
    public ApiResponse<Manual> manualCreate(@RequestBody Manual manual, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        manualMapper.insert(manual);
        return ApiResponse.success(manual);
    }

    @PutMapping("/manuals/{id}")
    public ApiResponse<Manual> manualUpdate(@PathVariable Long id, @RequestBody Manual manual, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        manual.setId(id);
        manualMapper.updateById(manual);
        return ApiResponse.success(manual);
    }

    @DeleteMapping("/manuals/{id}")
    public ApiResponse<Void> manualDelete(@PathVariable Long id, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        manualMapper.deleteById(id);
        return ApiResponse.success();
    }

    // ── 赛事 ──────────────────────────────────────────────────────────────
    @GetMapping("/tournaments")
    public ApiResponse<List<Tournament>> tournamentList(HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        return ApiResponse.success(tournamentMapper.selectList(new QueryWrapper<Tournament>().orderByDesc("year")));
    }

    @PostMapping("/tournaments")
    public ApiResponse<Tournament> tournamentCreate(@RequestBody Tournament t, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        tournamentMapper.insert(t);
        return ApiResponse.success(t);
    }

    @PutMapping("/tournaments/{id}")
    public ApiResponse<Tournament> tournamentUpdate(@PathVariable Long id, @RequestBody Tournament t, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        t.setId(id);
        tournamentMapper.updateById(t);
        return ApiResponse.success(t);
    }

    @DeleteMapping("/tournaments/{id}")
    public ApiResponse<Void> tournamentDelete(@PathVariable Long id, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        tournamentGameMapper.delete(new QueryWrapper<TournamentGame>().eq("tournament_id", id));
        tournamentMapper.deleteById(id);
        return ApiResponse.success();
    }

    // ── 棋手 ──────────────────────────────────────────────────────────────
    @GetMapping("/players")
    public ApiResponse<List<Player>> playerList(HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        return ApiResponse.success(playerMapper.selectList(new QueryWrapper<Player>().orderByAsc("id")));
    }

    @PostMapping("/players")
    public ApiResponse<Player> playerCreate(@RequestBody Player p, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        playerMapper.insert(p);
        return ApiResponse.success(p);
    }

    @PutMapping("/players/{id}")
    public ApiResponse<Player> playerUpdate(@PathVariable Long id, @RequestBody Player p, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        p.setId(id);
        playerMapper.updateById(p);
        return ApiResponse.success(p);
    }

    @DeleteMapping("/players/{id}")
    public ApiResponse<Void> playerDelete(@PathVariable Long id, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        playerMapper.deleteById(id);
        return ApiResponse.success();
    }

    // ── 资讯 ──────────────────────────────────────────────────────────────
    @GetMapping("/news")
    public ApiResponse<List<News>> newsList(HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        return ApiResponse.success(newsMapper.selectList(new QueryWrapper<News>().orderByDesc("id")));
    }

    @PostMapping("/news")
    public ApiResponse<News> newsCreate(@RequestBody News n, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        newsMapper.insert(n);
        return ApiResponse.success(n);
    }

    @PutMapping("/news/{id}")
    public ApiResponse<News> newsUpdate(@PathVariable Long id, @RequestBody News n, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        n.setId(id);
        newsMapper.updateById(n);
        return ApiResponse.success(n);
    }

    @DeleteMapping("/news/{id}")
    public ApiResponse<Void> newsDelete(@PathVariable Long id, HttpServletRequest req) {
        if (!isAdmin(req)) return ApiResponse.fail(403, "无权限");
        newsMapper.deleteById(id);
        return ApiResponse.success();
    }

    private boolean isAdmin(HttpServletRequest req) {
        User user = (User) req.getAttribute("currentUser");
        return user != null && "ADMIN".equals(user.getRole());
    }
}
