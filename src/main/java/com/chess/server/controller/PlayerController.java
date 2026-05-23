package com.chess.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.Player;
import com.chess.server.mapper.PlayerMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerMapper playerMapper;
    private final ObjectMapper objectMapper;

    public PlayerController(PlayerMapper playerMapper, ObjectMapper objectMapper) {
        this.playerMapper = playerMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ApiResponse<List<Map<String, Object>>> list() {
        List<Player> players = playerMapper.selectList(new QueryWrapper<Player>().orderByAsc("id"));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Player p : players) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", p.getId());
            map.put("name", p.getName());
            map.put("title", p.getTitle());
            map.put("era", p.getEra());
            map.put("eraClass", p.getEraClass());
            map.put("birthYear", p.getBirthYear());
            map.put("bio", p.getBio());
            try {
                map.put("manuals", objectMapper.readValue(
                        p.getManuals() != null ? p.getManuals() : "[]", List.class));
            } catch (Exception e) {
                map.put("manuals", new ArrayList<>());
            }
            result.add(map);
        }
        return ApiResponse.success(result);
    }
}
