package ir.corbika.repository;

import ir.corbika.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List findAllByStartedAndFinished(boolean started, boolean finished);
    List findAllByStarted(boolean started);
}

