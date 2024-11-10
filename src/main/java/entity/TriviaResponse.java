package entity;

import java.util.List;

/**
 * Represents a response containing a list of trivia questions.
 */
public class TriviaResponse {
    private List<TriviaQuestion> questions;

    public TriviaResponse(List<TriviaQuestion> questions) {
        this.questions = questions;
    }

    public List<TriviaQuestion> getQuestions() {
        return questions;
    }
}
