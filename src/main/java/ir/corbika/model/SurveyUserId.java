package ir.corbika.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SurveyUserId implements Serializable {

    @Column(name = "survey_id")
    private Long surveyId;

    @Column(name = "user_id")
    private Long userId;

    private SurveyUserId() {
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public SurveyUserId(
            Long surveyId,
            Long userId) {
        this.surveyId = surveyId;
        this.userId = userId;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        SurveyUserId that = (SurveyUserId) o;
        return Objects.equals(surveyId, that.surveyId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surveyId, userId);
    }
}
