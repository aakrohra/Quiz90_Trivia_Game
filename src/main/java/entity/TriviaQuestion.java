package entity;

import java.util.List;

/**
 * Represents a trivia question with its correct and incorrect answers.
 */
public class TriviaQuestion {
    private final String question;
    private final String correctAnswer;
    private final List<String> incorrectAnswers;

    public TriviaQuestion(String question, String correctAnswer, List<String> incorrectAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
