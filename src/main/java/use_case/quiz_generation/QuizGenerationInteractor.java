package use_case.quiz_generation;

/**
 * Interactor for the Quiz Generation Use Case.
 */
public class QuizGenerationInteractor implements QuizGenerationInputBoundary {

    private final QuizGenerationOutputBoundary quizGenerationPresenter;

    public QuizGenerationInteractor(QuizGenerationOutputBoundary quizGenerationPresenter) {
        this.quizGenerationPresenter = quizGenerationPresenter;
    }

    /**
     * Executes the action to switch to the Quiz Generation view.
     */
    @Override
    public void switchToQuizGenerationView() {
        quizGenerationPresenter.switchToQuizGenerationView();
    }

    /**
     * Executes the action to switch to the Main Menu view.
     */
    @Override
    public void switchToMainMenuView() {
        quizGenerationPresenter.switchToMainMenuView();
    }
}
