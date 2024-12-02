package use_case.local_multiplayer;

import entity.TriviaQuiz;
import interface_adapter.local_multiplayer_playthrough.LocalMultiplayerPlaythroughState;
import use_case.quiz_generation.QuizGenerationDataAccessInterface;
import use_case.quiz_generation.QuizGenerationInputData;

/**
 * Interactor for Local Multiplayer Use Case.
 */
public class LocalMultiplayerInteractor implements LocalMultiplayerInputBoundary {

    private final LocalMultiplayerOutputBoundary localMultiplayerPresenter;
    private final QuizGenerationDataAccessInterface triviaDataAccessObject;

    public LocalMultiplayerInteractor(LocalMultiplayerOutputBoundary localMultiplayerPresenter,
                                      QuizGenerationDataAccessInterface triviaDataAccessObject) {
        this.localMultiplayerPresenter = localMultiplayerPresenter;
        this.triviaDataAccessObject = triviaDataAccessObject;
    }

    /**
     * Executes quiz generation for Local Multiplayer.
     */
    @Override
    public void execute(QuizGenerationInputData localMultiplayerInputData) {
        try {
            final TriviaQuiz trivia = triviaDataAccessObject.getTrivia(localMultiplayerInputData);
            localMultiplayerPresenter.prepareQuiz(trivia);
        }
        catch (Exception ex) {
            localMultiplayerPresenter.prepareFailView(ex.getMessage());
        }
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

    /**
     * Executes the action to go to the next question in the quiz.
     */
    @Override
    public void nextQuestion(LocalMultiplayerPlaythroughState state) {
        localMultiplayerPresenter.nextQuestion(state);
    }

}
