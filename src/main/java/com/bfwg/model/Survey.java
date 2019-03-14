package com.bfwg.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Entity(name = "Survey")
@Table(name="survey")
public class Survey {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String name;

    @Column(name = "description")
    private String description;


    @Column(name = "started")
    private boolean started = false;

    @Column(name = "finished")
    private boolean finished = false;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SurveyChoice> choices;

    @OneToMany(
            mappedBy = "survey",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SurveyUser> participants = new ArrayList<>();

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

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<SurveyChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<SurveyChoice> choices) {
        this.choices = choices;
    }

    public List<SurveyUser> getParticipants() {
        return participants;
    }

    public void setParticipants(List<SurveyUser> participants) {
        this.participants = participants;
    }


    public void clearChoices() {
        this.choices.clear();
    }

    public void addChoice(SurveyChoice choice) {
        this.choices.add(choice);
    }


    public void addUser(User user) {
        SurveyUser surveyUser = new SurveyUser(this, user);
        participants.add(surveyUser);
//        user.getSurveys().add(surveyUser);
    }


    public void removeUser(User user) {
        for (Iterator<SurveyUser> iterator = participants.iterator();
             iterator.hasNext(); ) {
            SurveyUser surveyUser = iterator.next();

            if (surveyUser.getUser().equals(user) &&
                    surveyUser.getSurvey().equals(this)) {
                iterator.remove();
                surveyUser.getSurvey().getParticipants().remove(surveyUser);
                surveyUser.setUser(null);
                surveyUser.setSurvey(null);
            }
        }
    }

}
