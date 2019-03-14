package com.bfwg.repository;

import com.bfwg.model.Survey;
import com.bfwg.model.SurveyChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyChoiceRepository extends JpaRepository<SurveyChoice, Long> {
}

