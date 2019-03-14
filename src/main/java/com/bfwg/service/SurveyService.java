package com.bfwg.service;

import com.bfwg.model.Survey;
import com.bfwg.model.dto.SurveyContainer;
import com.bfwg.model.dto.SurveyResultContainer;


public interface SurveyService {


    void saveSurvey(Survey survey);


    void finish(Long id);

    void start(Long id);


    SurveyContainer getMySurvey(String username);

    void vote(String username, Long surveyId, Long vote);

    SurveyResultContainer getResult();
}
