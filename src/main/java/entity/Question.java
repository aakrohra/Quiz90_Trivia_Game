package entity;

import java.util.List;

/**
 * Representation of a question in our program.
 */
public interface Question {
    /**
     * Returns the String representation of the question.
     * @return the String representation of the question.
     */
    String getQuestionText();

    /**
     * Returns String representation of the answer options of the question.
     * @return String representation of the answer options of the question.
     */
    String getAnswerOptions();

    /**
     * Returns the correct answer to the question.
     * @return the correct answer to the question.
     */
    String getCorrectAnswer();

    /**
     * Returns the incorrect answers as a list.
     * @return the incorrect answers
     */
    List<String> getIncorrectAnswers();

}
