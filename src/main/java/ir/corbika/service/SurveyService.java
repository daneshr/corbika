package ir.corbika.service;

import ir.corbika.model.Survey;
import ir.corbika.model.dto.SurveyContainer;
import ir.corbika.model.dto.SurveyResultContainer;


public interface SurveyService {


    void saveSurvey(Survey survey);


    void finish();

    void start();

    SurveyContainer getMySurvey(String username);

    void vote(String username, Long surveyId, Long vote);

    SurveyResultContainer getResult();
}
