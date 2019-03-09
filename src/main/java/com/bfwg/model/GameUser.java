package com.bfwg.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by fan.jin on 2016-10-15.
 */

@Entity(name = "GameUser")
@Table(name="game_user")
public class GameUser {

    @EmbeddedId
    private GameUserId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("gameId")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @Column(name = "score")
    private int score;

    @Column(name = "asPlayer")
    private boolean asPlayer;

    public GameUser(Game game, User user) {
        this.user = user;
        this.game = game;
        this.id = new GameUserId(game.getId(), user.getId());
    }

    public GameUserId getId() {
        return id;
    }

    public void setId(GameUserId id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public boolean isAsPlayer() {
        return asPlayer;
    }

    public void setAsPlayer(boolean asPlayer) {
        this.asPlayer = asPlayer;
    }
}
