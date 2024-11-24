package entity;

import java.util.List;

/**
 * Represents a trivia question with its correct and incorrect answers.
 */
public class TriviaQuestion implements Question {
    private final String question;
    private final String correctAnswer;
    private final List<String> incorrectAnswers;

    public TriviaQuestion(String question, String correctAnswer, List<String> incorrectAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getQuestionText() {
        return question;
    }

    @Override
    public String getAnswerOptions() {
        final StringBuilder result = new StringBuilder();
        result.append(this.correctAnswer);
        for (String option : this.incorrectAnswers) {
            result.append(option).append("\n");
        }
        return result.toString();
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
