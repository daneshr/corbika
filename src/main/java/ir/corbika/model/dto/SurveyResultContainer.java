package ir.corbika.model.dto;

import java.util.List;

public class SurveyResultContainer {
    private List<SurveyChoiceResult> surveyChoiceResults;
    private String surveyName;

    private String surveyDescription;

    public List<SurveyChoiceResult> getSurveyChoiceResults() {
        return surveyChoiceResults;
    }

    public void setSurveyChoiceResults(List<SurveyChoiceResult> surveyChoiceResults) {
        this.surveyChoiceResults = surveyChoiceResults;
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
}
