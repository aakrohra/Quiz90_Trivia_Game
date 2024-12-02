package use_case.local_multiplayer;

import java.util.Map;

import entity.Quiz;
import entity.TriviaQuiz;
import kotlin.Pair;

/**
 * Output Boundary interface for the Local Multiplayer Use Case.
 */
public interface LocalMultiplayerOutputBoundary {

    /**
     * Prepares trivia quiz.
     * @param triviaQuiz The trivia quiz to be played.
     */
    void prepareQuiz(TriviaQuiz triviaQuiz);

    /**
     * Prepares the view to switch to the Local Multiplayer screen.
     */
    void switchToLocalMultiplayerView();

    /**
     * Prepares the view to switch to the Main Menu view.
     */
    void switchToMainMenuView();

    /**
     * Prepares the fail view for creating a quiz.
     * @param errorMessage The error message that shows up on the fail view.
     */
    void prepareFailView(String errorMessage);

    /**
     * Executes action to switch to Local Multiplayer Summary view.
     * @param quiz The completed quiz containing the questions and answers.
     * @param playerOneInfo A map of player one information.
     * @param playerTwoInfo A map of player two information.
     * @param numMapCorrect Array of correct answers.
     */
    void prepareLocalMultiplayerSummaryView(Quiz quiz,
                                            Map<Integer, Pair<String, Boolean>> playerOneInfo,
                                            Map<Integer, Pair<String, Boolean>> playerTwoInfo,
                                            Integer[] numMapCorrect);
}
