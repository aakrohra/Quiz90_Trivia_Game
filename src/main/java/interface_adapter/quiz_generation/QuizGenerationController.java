package interface_adapter.quiz_generation;

import use_case.quiz_generation.QuizGenerationInputBoundary;

/**
 * Controller for the Quiz Generation Use Case.
 */
public class QuizGenerationController {

    private final QuizGenerationInputBoundary quizGenerationInputBoundary;

    public QuizGenerationController(QuizGenerationInputBoundary quizGenerationInteractor) {
        this.quizGenerationInputBoundary = quizGenerationInteractor;
    }

    /**
     * Executes the action to switch to the Quiz Generation view.
     */
    public void switchToQuizGenerationView() {
        quizGenerationInputBoundary.switchToQuizGenerationView();
    }

    /**
     * Executes the action to switch to the Main Menu view.
     */
    public void switchToMainMenuView() {
        quizGenerationInputBoundary.switchToMainMenuView();
    }
}
