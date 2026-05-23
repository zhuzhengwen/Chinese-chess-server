package com.chess.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.News;
import com.chess.server.mapper.NewsMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsMapper newsMapper;

    public NewsController(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    @GetMapping
    public ApiResponse<List<News>> list(@RequestParam(required = false) String category) {
        QueryWrapper<News> qw = new QueryWrapper<News>().orderByDesc("id");
        if (category != null && !category.isEmpty()) qw.eq("category", category);
        return ApiResponse.success(newsMapper.selectList(qw));
    }
}
