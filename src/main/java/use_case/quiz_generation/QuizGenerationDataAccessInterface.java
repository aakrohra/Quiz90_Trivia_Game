package use_case.quiz_generation;

import entity.TriviaQuiz;

/**
 * DAO interface for the Quiz Generation Use Case.
 */
public interface QuizGenerationDataAccessInterface {

    /**
     * Retrieves a trivia quiz based on the given input data.
     *
     * @param quizData Contains the category, difficulty, and number of questions required for fetching trivia.
     * @return A TriviaQuiz object containing a list of TriviaQuestion objects.
     * @throws Exception If an error occurs during the API request or response parsing.
     */
    TriviaQuiz getTrivia(QuizGenerationInputData quizData) throws Exception;
}
