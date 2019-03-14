package ir.corbika.model.dto;

import ir.corbika.model.Survey;
import ir.corbika.model.SurveyChoice;

import java.util.List;

public class SurveyContainer {
    private Long myOpinion;

    private String surveyName;

    private String surveyDescription;

    private List<SurveyChoice> choices;

    public Long getMyOpinion() {
        return myOpinion;
    }

    public void setMyOpinion(Long myOpinion) {
        this.myOpinion = myOpinion;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getSurveyDescription() {
        return surveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
    }

    public List<SurveyChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<SurveyChoice> choices) {
        this.choices = choices;
    }

    public SurveyContainer() {
    }

    public SurveyContainer(Long myOpinion, Survey survey) {
        this.myOpinion = myOpinion;
        this.surveyName = survey.getName();
        this.surveyDescription = survey.getDescription();
        this.choices = survey.getChoices();
    }

}
