package com.bfwg.model;

import javax.persistence.*;


@Entity(name = "SurveyUser")
@Table(name="survey_player")
public class SurveyUser {

    @EmbeddedId
    private SurveyUserId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("surveyId")
    private Survey survey;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @Column(name = "opinion")
    private Long opinion = 0l;


    public SurveyUser(Survey survey, User user) {
        this.user = user;
        this.survey = survey;
        this.id = new SurveyUserId(survey.getId(), user.getId());
    }

    public SurveyUser(){

    }
    public SurveyUserId getId() {
        return id;
    }

    public void setId(SurveyUserId id) {
        this.id = id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getOpinion() {
        return opinion;
    }

    public void setOpinion(Long opinion) {
        this.opinion = opinion;
    }

}
