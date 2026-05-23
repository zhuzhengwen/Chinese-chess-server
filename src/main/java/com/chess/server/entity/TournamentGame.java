package com.chess.server.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName("tournament_games")
public class TournamentGame {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tournamentId;
    private String round;
    private String redPlayer;
    private String blackPlayer;
    private String result;
    private String opening;
    private String date;
    private String moves;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTournamentId() { return tournamentId; }
    public void setTournamentId(Long tournamentId) { this.tournamentId = tournamentId; }
    public String getRound() { return round; }
    public void setRound(String round) { this.round = round; }
    public String getRedPlayer() { return redPlayer; }
    public void setRedPlayer(String redPlayer) { this.redPlayer = redPlayer; }
    public String getBlackPlayer() { return blackPlayer; }
    public void setBlackPlayer(String blackPlayer) { this.blackPlayer = blackPlayer; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getOpening() { return opening; }
    public void setOpening(String opening) { this.opening = opening; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getMoves() { return moves; }
    public void setMoves(String moves) { this.moves = moves; }
}
