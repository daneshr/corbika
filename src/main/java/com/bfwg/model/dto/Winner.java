package com.bfwg.model.dto;

public class Winner {
    private Long gameId;
    private Long winnerId;

    public Long getGameId() {
        return gameId;
    }

    public void seGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }
}
