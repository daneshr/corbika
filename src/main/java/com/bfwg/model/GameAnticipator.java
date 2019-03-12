package com.bfwg.model;

import javax.persistence.*;
import java.util.List;


@Entity(name = "GameAnticipator")
@Table(name="game_anticipator")
public class GameAnticipator {

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

    @Column(name = "vote")
    private Long vote;

    public GameAnticipator(Game game, User user) {
        this.user = user;
        this.game = game;
        this.id = new GameUserId(game.getId(), user.getId());
    }

    public GameAnticipator(){

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


    public Long getVote() {
        return vote;
    }

    public void setVote(Long vote) {
        this.vote = vote;
    }
}
