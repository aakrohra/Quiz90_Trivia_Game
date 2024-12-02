package use_case.local_multiplayer;

import entity.Quiz;
import entity.TriviaQuiz;
import kotlin.Pair;
import use_case.quiz_generation.QuizGenerationDataAccessInterface;
import use_case.quiz_generation.QuizGenerationInputData;

import java.util.Map;

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
        try {
            final TriviaQuiz trivia = triviaDataAccessObject.getTrivia(localMultiplayerInputData);
            localMultiplayerPresenter.prepareQuiz(trivia);
        }
        catch (Exception ex) {
            // Log the exception or handle it based on your use case
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
     * Executes action to switch to Local Multiplayer Summary view.
     * @param quiz The completed quiz containing the questions and answers.
     * @param playerOneInfo A map of player one information.
     * @param playerTwoInfo A map of player two information.
     * @param numMapCorrect Array of correct answers.
     */
    @Override
    public void prepareLocalMultiplayerSummaryView(Quiz quiz, Map<Integer,
            Pair<String, Boolean>> playerOneInfo, Map<Integer, Pair<String, Boolean>> playerTwoInfo,
                                                   Integer[] numMapCorrect) {
        localMultiplayerPresenter.prepareLocalMultiplayerSummaryView(quiz,
                playerOneInfo, playerTwoInfo, numMapCorrect);
    }
}
