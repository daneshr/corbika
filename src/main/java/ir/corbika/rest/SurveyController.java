package ir.corbika.rest;

import ir.corbika.model.Survey;
import ir.corbika.model.dto.*;
import ir.corbika.model.dto.IdContainer;
import ir.corbika.model.dto.SurveyContainer;
import ir.corbika.model.dto.SurveyResultContainer;
import ir.corbika.model.dto.SurveySelection;
import ir.corbika.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SurveyController {

    @Autowired
    private SurveyService surveyService;


    @RequestMapping(value = "/survey/admin/add", method = RequestMethod.POST, consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addSurvey(@RequestBody Survey survey) {
        surveyService.saveSurvey(survey);
        return ResponseEntity.accepted().body("ok");
    }

    @RequestMapping(value = "/survey/admin/finish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateWinner() {
        surveyService.finish();
        return ResponseEntity.accepted().body("ok");
    }


    @RequestMapping(value = "/survey/admin/start", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> start() {
        surveyService.start();
        return ResponseEntity.accepted().body("ok");
    }


    @RequestMapping(value = "/survey/mine", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public SurveyContainer mine(Principal user) {

        return surveyService.getMySurvey(user.getName());
    }

    @RequestMapping("/survey/vote")
    @PreAuthorize("hasRole('USER')")
    public void vote(Principal user,@RequestBody SurveySelection vote) {
        String username = user.getName();
        surveyService.vote(username,vote.getSurveyId(),vote.getVote());
    }

    @RequestMapping(value = "/survey/result", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public SurveyResultContainer result() {
        return surveyService.getResult();
    }


}
