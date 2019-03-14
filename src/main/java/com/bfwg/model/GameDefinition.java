package com.bfwg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity(name = "gameDefinition")
@Table(name="game_definition")
public class GameDefinition  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "anticipateWinScore")
    private int anticipateWinScore=2;

    @Column(name = "anticipateLoseScore")
    private int anticipateLoseScore=0;

    @Column(name = "playersCount")
    private int playersCount=0;

    @Column(name = "maxWinners")
    private int maxWinners;

    @Column(name = "winnerScore")
    private int winnerScore;

    @Column(name = "answer")
    private Long answer;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Choice> choices;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAnticipateWinScore() {
        return anticipateWinScore;
    }

    public void setAnticipateWinScore(int anticipateWinScore) {
        this.anticipateWinScore = anticipateWinScore;
    }

    public int getAnticipateLoseScore() {
        return anticipateLoseScore;
    }

    public void setAnticipateLoseScore(int anticipateLoseScore) {
        this.anticipateLoseScore = anticipateLoseScore;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public int getMaxWinners() {
        return maxWinners;
    }

    public void setMaxWinners(int maxWinners) {
        this.maxWinners = maxWinners;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public void setWinnerScore(int winnerScore) {
        this.winnerScore = winnerScore;
    }


    public Long getAnswer() {
        return answer;
    }

    public void setAnswer(Long answer) {
        this.answer = answer;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public void clearChoices() {
        this.choices.clear();
    }
    public void addChoice(Choice choice) {
        this.choices.add(choice);
    }

}
