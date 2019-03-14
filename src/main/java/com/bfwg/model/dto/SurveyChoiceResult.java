package com.bfwg.model.dto;

import com.bfwg.model.SurveyChoice;

public class SurveyChoiceResult {
    private SurveyChoice surveyChoice;
    private int result;

    public SurveyChoice getSurveyChoice() {
        return surveyChoice;
    }

    public void setSurveyChoice(SurveyChoice surveyChoice) {
        this.surveyChoice = surveyChoice;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
