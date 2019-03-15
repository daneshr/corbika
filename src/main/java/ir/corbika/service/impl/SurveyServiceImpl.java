package ir.corbika.service.impl;

import ir.corbika.model.*;
import ir.corbika.model.dto.SurveyChoiceResult;
import ir.corbika.model.dto.SurveyContainer;
import ir.corbika.model.dto.SurveyResultContainer;
import ir.corbika.repository.SurveyChoiceRepository;
import ir.corbika.repository.SurveyRepository;
import ir.corbika.repository.SurveyUserRepository;
import ir.corbika.repository.UserRepository;
import ir.corbika.service.SurveyService;
import ir.corbika.model.Survey;
import ir.corbika.model.SurveyUser;
import ir.corbika.model.SurveyUserId;
import ir.corbika.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyChoiceRepository surveyChoiceRepository;

    @Autowired
    private SurveyUserRepository surveyUserRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void saveSurvey(Survey survey) {
        surveyRepository.saveAndFlush(survey);
    }

    @Override
    public void finish() {
        List<Survey> surveys = surveyRepository.findAll();

        Survey candidateSurvey =null;
        for (Survey survey:surveys) {
            if (survey.isStarted() && !survey.isFinished()){
                candidateSurvey = survey;
                break;
            }
        }
        if (candidateSurvey == null) {
            throw new RuntimeException("no suitable found!");
        }
//
//        if (!candidateSurvey.isStarted()) {
//            throw new RuntimeException("survey has not been started");
//        }else if (candidateSurvey.isFinished()) {
//            throw new RuntimeException("survey already has finished!");
//        }
        candidateSurvey.setStarted(true);
        candidateSurvey.setFinished(true);

        surveyRepository.saveAndFlush(candidateSurvey);
    }

    @Override
    public void start() {
        List<Survey> surveys = surveyRepository.findAll();

        Survey candidateSurvey =null;
        for (Survey survey:surveys) {
            if (!survey.isStarted() || survey.isFinished()){
                candidateSurvey = survey;
                break;
            }
        }
        if (candidateSurvey == null) {
            throw new RuntimeException("survey is running!");
        }
        candidateSurvey.setStarted(true);
        candidateSurvey.setFinished(false);
        List<User> users = userRepository.findAll();

        Survey finalCandidateSurvey = candidateSurvey;
        users.forEach(user -> finalCandidateSurvey.addUser(user));

        surveyRepository.save(candidateSurvey);

    }

    @Override
    public SurveyContainer getMySurvey(String username) {
        User user = userRepository.findByUsername(username);
        List<SurveyUser> surveys= user.getSurveys();
        Survey candidateSurvey = null;
        Long myOpinion = 0l;

        for (SurveyUser surveyUser:surveys) {
            if(surveyUser.getSurvey().isStarted() && !surveyUser.getSurvey().isFinished()){
                candidateSurvey =surveyUser.getSurvey();
                myOpinion=surveyUser.getOpinion();
            }
        }
        if (candidateSurvey == null){
            throw new RuntimeException("survey not found!");
        }
        return new SurveyContainer(myOpinion,candidateSurvey);
    }

    @Override
    public void vote(String username, Long surveyId, Long vote) {
        User user = userRepository.findByUsername(username);
        SurveyUser surveyUser = surveyUserRepository.findOne(new SurveyUserId(surveyId, user.getId()));
        if (surveyUser ==null || surveyUser.getSurvey() ==null) {
            throw new RuntimeException("survey not found!");
        } else if (!surveyUser.getSurvey().isStarted()) {
            throw new RuntimeException("survey is not started!");
        } else if (surveyUser.getSurvey().isFinished()) {
            throw new RuntimeException("voting time is over!");
        }
        surveyUser.setOpinion(vote);
        surveyUserRepository.save(surveyUser);
    }

    @Override
    public SurveyResultContainer getResult() {
        List<Object[]> res = surveyUserRepository.result();
        List<SurveyChoiceResult> surveyChoiceResults = new ArrayList<>();
        for (Object[] obj:res) {
            Long choice = (Long) obj[0];
            if (choice == 0) {
                continue;
            }
            Long count = (Long) obj[1];
            SurveyChoiceResult tmp = new SurveyChoiceResult();
            tmp.setResult(count.intValue());
            tmp.setSurveyChoice(surveyChoiceRepository.findOne(choice));
            surveyChoiceResults.add(tmp);
        }

        SurveyResultContainer surveyResultContainer = new SurveyResultContainer();
        surveyResultContainer.setSurveyChoiceResults(surveyChoiceResults);
        Survey survey = (Survey) surveyRepository.findAllByStarted(true).get(0);
        surveyResultContainer.setSurveyName(survey.getName());
        surveyResultContainer.setSurveyDescription(survey.getDescription());
        return surveyResultContainer;

    }
}
