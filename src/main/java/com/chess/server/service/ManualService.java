package com.chess.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.server.entity.Manual;
import com.chess.server.mapper.ManualMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ManualService {

    private final ManualMapper manualMapper;

    public ManualService(ManualMapper manualMapper) {
        this.manualMapper = manualMapper;
    }

    public List<Manual> search(String category, String keyword, Integer difficulty) {
        QueryWrapper<Manual> qw = new QueryWrapper<>();
        if (category != null && !category.isEmpty()) qw.eq("category", category);
        if (difficulty != null) qw.eq("difficulty", difficulty);
        if (keyword != null && !keyword.isEmpty()) qw.like("title", keyword);
        return manualMapper.selectList(qw);
    }

    public List<Manual> getRecommended() {
        return manualMapper.selectList(new QueryWrapper<Manual>().orderByDesc("view_count").last("LIMIT 6"));
    }

    public Manual getById(Long id) {
        Manual m = manualMapper.selectById(id);
        if (m != null) {
            m.setViewCount(m.getViewCount() == null ? 1 : m.getViewCount() + 1);
            manualMapper.updateById(m);
        }
        return m;
    }

    public List<Map<String, Object>> getCategories() {
        List<Map<String, Object>> list = new ArrayList<>();
        Object[][] cats = {
            {"cat1", "四大名谱", 48}, {"cat2", "中局名局", 36}, {"cat3", "残局精华", 52},
            {"cat4", "开局布阵", 44}, {"cat5", "名家对局", 28}, {"cat6", "古典残局", 60}
        };
        for (Object[] c : cats) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", c[0]); map.put("name", c[1]); map.put("count", c[2]);
            list.add(map);
        }
        return list;
    }
}
