package com.chess.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.server.dto.ApiResponse;
import com.chess.server.entity.Tournament;
import com.chess.server.entity.TournamentGame;
import com.chess.server.mapper.TournamentGameMapper;
import com.chess.server.mapper.TournamentMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentMapper tournamentMapper;
    private final TournamentGameMapper tournamentGameMapper;
    private final ObjectMapper objectMapper;

    public TournamentController(TournamentMapper tournamentMapper,
                                TournamentGameMapper tournamentGameMapper,
                                ObjectMapper objectMapper) {
        this.tournamentMapper = tournamentMapper;
        this.tournamentGameMapper = tournamentGameMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ApiResponse<List<Map<String, Object>>> list() {
        List<Tournament> list = tournamentMapper.selectList(
                new QueryWrapper<Tournament>().orderByDesc("year"));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Tournament t : list) {
            result.add(buildTournamentMap(t));
        }
        return ApiResponse.success(result);
    }

    @GetMapping("/game/{gameId}")
    public ApiResponse<Map<String, Object>> game(@PathVariable Long gameId) {
        TournamentGame g = tournamentGameMapper.selectById(gameId);
        if (g == null) return ApiResponse.fail(404, "对局不存在");
        Tournament t = tournamentMapper.selectById(g.getTournamentId());

        Map<String, Object> gameInfo = new LinkedHashMap<>();
        gameInfo.put("id", g.getId());
        gameInfo.put("round", g.getRound());
        gameInfo.put("redPlayer", g.getRedPlayer());
        gameInfo.put("blackPlayer", g.getBlackPlayer());
        gameInfo.put("result", g.getResult());
        gameInfo.put("opening", g.getOpening());
        gameInfo.put("date", g.getDate());
        gameInfo.put("tournamentId", g.getTournamentId());
        gameInfo.put("tournamentName", t != null ? t.getName() : "");

        List<?> moves;
        try {
            moves = objectMapper.readValue(g.getMoves() != null ? g.getMoves() : "[]", List.class);
        } catch (Exception e) {
            moves = new ArrayList<>();
        }

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("gameInfo", gameInfo);
        resp.put("moves", moves);
        return ApiResponse.success(resp);
    }

    @GetMapping("/{id}")
    public ApiResponse<Map<String, Object>> detail(@PathVariable Long id) {
        Tournament t = tournamentMapper.selectById(id);
        if (t == null) return ApiResponse.fail(404, "赛事不存在");
        return ApiResponse.success(buildTournamentMap(t));
    }

    private Map<String, Object> buildTournamentMap(Tournament t) {
        List<TournamentGame> games = tournamentGameMapper.selectList(
                new QueryWrapper<TournamentGame>().eq("tournament_id", t.getId()));
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", t.getId());
        map.put("name", t.getName());
        map.put("year", t.getYear());
        map.put("location", t.getLocation());
        map.put("champion", t.getChampion());
        map.put("games", games);
        return map;
    }
}
