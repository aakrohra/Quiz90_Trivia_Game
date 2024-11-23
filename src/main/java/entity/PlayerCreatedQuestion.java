package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the question interface.
 * Entity that represents a question object which was pulled from the Trivia API.
 */
public class PlayerCreatedQuestion implements Question {
    private final String question;
    private final List<String> answerOptions = new ArrayList<>();
    private final String correctAnswer;

    public PlayerCreatedQuestion(String question, List<String> answerOptions, String correctAnswer) {
        this.question = question;
        this.answerOptions.addAll(answerOptions);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getQuestion() {
        return this.question;
    }

    @Override
    public String getAnswerOptions() {
        final StringBuilder result = new StringBuilder();
        for (String option : this.answerOptions) {
            result.append(option).append("\n");
        }
        return result.toString();
    }

    public List<String> getAnswerOptionsList() {
        return this.answerOptions;
    }

    @Override
    public String getCorrectAnswer() {
        return this.correctAnswer;
    }
}
