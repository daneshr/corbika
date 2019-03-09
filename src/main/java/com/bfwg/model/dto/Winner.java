package com.bfwg.model.dto;

public class Winner {
    private Long gameId;
    private String winner;

    public Long getGameId() {
        return gameId;
    }

    public void seGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
