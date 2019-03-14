package com.bfwg.repository;

import com.bfwg.model.SurveyUser;
import com.bfwg.model.SurveyUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyUserRepository extends JpaRepository<SurveyUser, SurveyUserId> {
    @Query("SELECT opinion,count(opinion) FROM SurveyUser GROUP BY opinion ")
    List result();

}

