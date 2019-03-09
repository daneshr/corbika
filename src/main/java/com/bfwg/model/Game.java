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

    @Column(name = "running")
    private boolean running = false;


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
    private List<GameUser> players = new ArrayList<>();

    @OneToMany(
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<GameUser> anticipators = new ArrayList<>();

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

    public List<GameUser> getPlayers() {
        return players;
    }

    public void setPlayers(List<GameUser> players) {
        this.players = players;
    }

    public List<GameUser> getAnticipators() {
        return anticipators;
    }

    public void setAnticipators(List<GameUser> anticipators) {
        this.anticipators = anticipators;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void addUserAsAnticipator(User user) {
        GameUser gameUser = new GameUser(this, user);
        gameUser.setAsPlayer(false);
        anticipators.add(gameUser);
        user.getGames().add(gameUser);
    }

    public void addUserAsPlayer(User user) {
        GameUser gameUser = new GameUser(this, user);
        gameUser.setAsPlayer(true);
        players.add(gameUser);
        user.getGames().add(gameUser);
    }

    public void removeAnticipatorGame(User user) {
        removeUser(user,anticipators);
    }
    public void removePlayerGame(User user) {
        removeUser(user,players);
    }

    private void removeUser(User user,List<GameUser> players){
        for (Iterator<GameUser> iterator = players.iterator();
             iterator.hasNext(); ) {
            GameUser gameUser = iterator.next();

            if (gameUser.getUser().equals(this) &&
                    gameUser.getGame().equals(user)) {
                iterator.remove();
                gameUser.getGame().getAnticipators().remove(gameUser);
                gameUser.setUser(null);
                gameUser.setGame(null);
            }
        }
    }
}
