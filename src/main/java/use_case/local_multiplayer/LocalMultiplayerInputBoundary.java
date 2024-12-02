package use_case.local_multiplayer;

import entity.Quiz;
import kotlin.Pair;
import use_case.quiz_generation.QuizGenerationInputData;

import java.util.Map;

/**
 * Input Boundary for actions related to local multiplayer.
 */
public interface LocalMultiplayerInputBoundary {

    /**
     * Executes quiz generation for Local Multiplayer Use Case.
     * @param localMultiplayerInputData the input data for generating the quiz.
     */
    void execute(QuizGenerationInputData localMultiplayerInputData);

    /**
     * Executes action to switch to Local Multiplayer view.
     */
    void switchToLocalMultiplayerView();

    /**
     * Executes the action to switch to the Main Menu view.
     */
    void switchToMainMenuView();

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
