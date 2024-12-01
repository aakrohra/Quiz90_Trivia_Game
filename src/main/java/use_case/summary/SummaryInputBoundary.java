package use_case.summary;

import entity.Quiz;
import kotlin.Pair;

import java.util.Map;

/**
 * Input Boundary for actions related to summary navigation and handling.
 */
public interface SummaryInputBoundary {

    /**
     * Executes the action to switch to the Summary view.
     *
     * @param quiz The completed quiz containing the questions and answers.
     * @param numOfCorrectAnswers The number of correct answers achieved by the user.
     * @param playerInfo A map of player information.
     */
    void execute(Quiz quiz, int numOfCorrectAnswers, Map<Integer, Pair<String, Boolean>> playerInfo);

    /**
     * Executes the action to switch to the Main Menu view.
     */
    void switchToMainMenuView();
}
