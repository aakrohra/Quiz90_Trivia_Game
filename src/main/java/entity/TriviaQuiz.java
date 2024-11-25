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

    @Override
    public List<? extends Question> getQuestions() {
        return questions;
    }

    @Override
    public String getListOfQuestions() {
        final StringBuilder result = new StringBuilder();
        for (Question question : this.getQuestions()) {
            result.append(question.getQuestionText()).append("\n");
        }
        return result.toString();
    }
}
