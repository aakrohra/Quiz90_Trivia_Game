package use_case.quiz_generation;

import entity.Quiz;

/**
 * Output Boundary for the Quiz Generation Use Case.
 */
public interface QuizGenerationOutputBoundary {

    /**
     * Prepares the view for the Playthrough screen.
     * Updates the PlaythroughState with the trivia quiz and notifies the view.
     *
     * @param triviaQuiz The quiz to display in the playthrough view.
     */
    void prepareSuccessView(Quiz triviaQuiz);

    /**
     * Prepares the view to switch to the Quiz Generation screen.
     */
    void switchToQuizGenerationView();

    /**
     * Prepares the view to switch to the Main Menu screen.
     */
    void switchToMainMenuView();
}
