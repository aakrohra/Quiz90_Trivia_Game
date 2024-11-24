package entity;

import java.util.List;

/**
 * Represents a response containing a list of trivia questions.
 */
public class TriviaQuiz implements Quiz {
    private final List<TriviaQuestion> questions;

    public TriviaQuiz(List<TriviaQuestion> questions) {
        this.questions = questions;
    }

    public List<TriviaQuestion> getQuestions() {
        return questions;
    }

    @Override
    public String getListOfQuestions() {
        final StringBuilder result = new StringBuilder();
        for (TriviaQuestion question : this.getQuestions()) {
            result.append(question.getQuestionText()).append("\n");
        }
        return result.toString();
    }
}
