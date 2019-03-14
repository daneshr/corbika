package ir.corbika.model;

import javax.persistence.*;


@Entity(name = "GamePlayer")
@Table(name="game_player")
public class GamePlayer {

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


    public GamePlayer(Game game, User user) {
        this.user = user;
        this.game = game;
        this.id = new GameUserId(game.getId(), user.getId());
    }

    public GamePlayer(){

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

}
