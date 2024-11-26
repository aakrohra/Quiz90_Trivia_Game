package use_case.local_multiplayer;

import entity.TriviaQuiz;
import use_case.quiz_generation.QuizGenerationDataAccessInterface;
import use_case.quiz_generation.QuizGenerationInputData;

/**
 * Interactor for Local Multiplayer Use Case.
 */
public class LocalMultiplayerInteractor implements LocalMultiplayerInputBoundary {

    private final LocalMultiplayerOutputBoundary localMultiplayerPresenter;
    private final QuizGenerationDataAccessInterface triviaDataAccessObject;

    public LocalMultiplayerInteractor(LocalMultiplayerOutputBoundary localMultiplayerPresenter, QuizGenerationDataAccessInterface triviaDataAccessObject) {
        this.localMultiplayerPresenter = localMultiplayerPresenter;
        this.triviaDataAccessObject = triviaDataAccessObject;
    }

    /**
     * Executes quiz generation for Local Multiplayer.
     */
    @Override
    public void execute(QuizGenerationInputData localMultiplayerInputData) {
        final TriviaQuiz trivia = triviaDataAccessObject.getTrivia(localMultiplayerInputData);
        localMultiplayerPresenter.prepareQuiz(trivia);
    }

    /**
     * Executes the action to switch to the Local Multiplayer view.
     */
    @Override
    public void switchToLocalMultiplayerView() {
        localMultiplayerPresenter.switchToLocalMultiplayerView();
    }

    /**
     * Executes the action to switch to the Main Menu view.
     */
    @Override
    public void switchToMainMenuView() {
        localMultiplayerPresenter.switchToMainMenuView();
    }
}
