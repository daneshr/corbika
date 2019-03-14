package ir.corbika.model.dto;

import ir.corbika.model.GameDefinition;

public class RunningGame {
    private Long id;
    private GameDefinition gameDefinition;

    public RunningGame(Long id, GameDefinition gameDefinition) {
        this.id = id;
        this.gameDefinition = gameDefinition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameDefinition getGameDefinition() {
        return gameDefinition;
    }

    public void setGameDefinition(GameDefinition gameDefinition) {
        this.gameDefinition = gameDefinition;
    }
}
