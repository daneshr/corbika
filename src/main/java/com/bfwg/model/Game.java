package com.bfwg.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fan.jin on 2016-10-15.
 */

@Entity(name = "Game")
@Table(name="game")
public class Game {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private GameDefinition gameDefinition;

    @Column(name = "run")
    private boolean run = false;

    @Column(name = "votingClosed")
    private boolean votingClosed = false;


    @Column(name = "finished")
    private boolean finished = false;


    public Game() {
    }

    public Game(String name) {
        this.name = name;
    }


    @OneToMany(
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<GamePlayer> players = new ArrayList<>();

    @OneToMany(
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<GameAnticipator> anticipators = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameDefinition getGameDefinition() {
        return gameDefinition;
    }

    public void setGameDefinition(GameDefinition gameDefinition) {
        this.gameDefinition = gameDefinition;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<GamePlayer> players) {
        this.players = players;
    }

    public List<GameAnticipator> getAnticipators() {
        return anticipators;
    }

    public void setAnticipators(List<GameAnticipator> anticipators) {
        this.anticipators = anticipators;
    }


    public void addUserAsAnticipator(User user) {
        GameAnticipator gameUser = new GameAnticipator(this, user);
//        gameUser.setAsPlayer(false);
        anticipators.add(gameUser);
        user.getAnticipatedGames().add(gameUser);
    }

    public void addUserAsPlayer(User user) {
        GamePlayer gameUser = new GamePlayer(this, user);
//        gameUser.setAsPlayer(true);
        players.add(gameUser);
        user.getPlayedGames().add(gameUser);
    }

    public void removeAnticipatorGame(User user) {
        for (Iterator<GameAnticipator> iterator = anticipators.iterator();
             iterator.hasNext(); ) {
            GameAnticipator gameUser = iterator.next();

            if (gameUser.getUser().equals(user) &&
                    gameUser.getGame().equals(this)) {
                iterator.remove();
                gameUser.getGame().getAnticipators().remove(gameUser);
                gameUser.setUser(null);
                gameUser.setGame(null);
            }
        }
    }

    public void removePlayerGame(User user) {
        for (Iterator<GamePlayer> iterator = players.iterator();
             iterator.hasNext(); ) {
            GamePlayer gameUser = iterator.next();

            if (gameUser.getUser().equals(user) &&
                    gameUser.getGame().equals(this)) {
                iterator.remove();
                gameUser.getGame().getPlayers().remove(gameUser);
                gameUser.setUser(null);
                gameUser.setGame(null);
            }
        }
    }

    public boolean isVotingClosed() {
        return votingClosed;
    }

    public void setVotingClosed(boolean votingClosed) {
        this.votingClosed = votingClosed;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
