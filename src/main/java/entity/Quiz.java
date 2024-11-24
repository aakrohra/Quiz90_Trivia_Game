package entity;

import java.util.List;

/**
 * Representation of a quiz instance.
 */
public interface Quiz {

    /**
     * String representation of the list of trivia questions of the trivia quiz.
     * @return String representation of the list of trivia questions of the trivia quiz.
     */
    String getListOfQuestions();

    // TODO: why  does getListOfQuestions return a string? Shouldn't it return a list of questions like below
    // TODO: if this is only useful for playerCreatedQuiz we should remove it from the interface.

    /**
     * Getter for the instance variable that stores a list of questions.
     * @return the list of questions.
     */
    List<? extends Question> getQuestions();
}
