package entity;

import java.util.List;

public class TriviaResponse {
    private List<TriviaQuestion> questions;

    public TriviaResponse(List<TriviaQuestion> questions) {
        this.questions = questions;
    }

    public List<TriviaQuestion> getQuestions() {
        return questions;
    }
}
