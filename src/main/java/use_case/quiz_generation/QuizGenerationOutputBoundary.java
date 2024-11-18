package use_case.quiz_generation;

/**
 * Output Boundary for the Quiz Generation Use Case.
 */
public interface QuizGenerationOutputBoundary {

    /**
     * Prepares the view to switch to the Quiz Generation screen.
     */
    void switchToQuizGenerationView();

    /**
     * Prepares the view to switch to the Main Menu screen.
     */
    void switchToMainMenuView();
}
