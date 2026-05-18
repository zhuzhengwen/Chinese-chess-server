package com.chess.server.service;

import com.chess.server.entity.Manual;
import com.chess.server.repository.ManualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ManualService {

    private final ManualRepository manualRepository;

    public List<Manual> search(String category, String keyword, Integer difficulty) {
        String cat = (category != null && !category.isEmpty()) ? category : null;
        String kw = (keyword != null && !keyword.isEmpty()) ? keyword : null;
        return manualRepository.search(cat, difficulty, kw);
    }

    public Optional<Manual> getById(Long id) {
        return manualRepository.findById(id).map(m -> {
            m.setViewCount(m.getViewCount() + 1);
            return manualRepository.save(m);
        });
    }

    public List<Manual> importManuals(List<Manual> manuals) {
        manuals.forEach(m -> {
            if (m.getContent() != null && !m.getContent().isEmpty() && m.getTotalMoves() == 0) {
                try {
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    java.util.List<?> list = mapper.readValue(m.getContent(), java.util.List.class);
                    m.setTotalMoves(list.size());
                } catch (Exception ignored) {}
            }
        });
        return manualRepository.saveAll(manuals);
    }

    public List<Manual> getRecommended() {
        return manualRepository.findTop6ByOrderByViewCountDesc();
    }

    public List<Map<String, Object>> getCategories() {
        List<Map<String, Object>> categories = new ArrayList<>();
        String[][] cats = {
            {"cat1", "四大名谱", "48"},
            {"cat2", "中局名局", "36"},
            {"cat3", "残局精华", "52"},
            {"cat4", "开局布阵", "44"},
            {"cat5", "名家对局", "28"},
            {"cat6", "古典残局", "60"}
        };
        for (String[] c : cats) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", c[0]);
            map.put("name", c[1]);
            map.put("count", Integer.parseInt(c[2]));
            categories.add(map);
        }
        return categories;
    }
}
