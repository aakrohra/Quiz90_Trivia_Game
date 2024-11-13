package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the question interface.
 * Entity that represents a question object which was pulled from the Trivia API.
 */
public class PlayerCreatedQuestion implements Question {
    private final String questionText;
    private final List<String> answerOptions = new ArrayList<>();
    private final String correctAnswer;

    public PlayerCreatedQuestion(String questionText, List<String> answerOptions, String correctAnswer) {
        this.questionText = questionText;
        this.answerOptions.addAll(answerOptions);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getQuestionText() {
        return this.questionText;
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
