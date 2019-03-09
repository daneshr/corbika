package com.bfwg.model;

import javax.persistence.*;

@Entity(name = "Choice")
@Table(name = "choice")
public class Choice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "choice")
    private String choice;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("gameDefinitionId")
    private GameDefinition gameDefinition;

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public GameDefinition getGameDefinition() {
        return gameDefinition;
    }

    public void setGameDefinition(GameDefinition gameDefinition) {
        this.gameDefinition = gameDefinition;
    }
}
