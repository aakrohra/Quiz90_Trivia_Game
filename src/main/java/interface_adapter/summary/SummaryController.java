package interface_adapter.summary;

import entity.Quiz;
import kotlin.Pair;
import use_case.summary.SummaryInputBoundary;

import java.util.Map;

/**
 * Controller for the Summary Use Case.
 */
public class SummaryController {

    private final SummaryInputBoundary summaryInteractor;

    public SummaryController(SummaryInputBoundary summaryInteractor) {
        this.summaryInteractor = summaryInteractor;
    }

    /**
     * Executes the action to switch to the Summary view.
     *
     * @param quiz The completed quiz containing the questions and answers.
     * @param numOfCorrectAnswers The number of correct answers achieved by the user.
     * @param playerInfo A map of player information.
     */
    public void prepareSummaryView(Quiz quiz, int numOfCorrectAnswers, Map<Integer, Pair<String, Boolean>> playerInfo) {
        summaryInteractor.prepareSummaryView(quiz, numOfCorrectAnswers, playerInfo);
    }

    /**
     * Executes the action to switch to the Main Menu view.
     */
    public void switchToMainMenuView() {
        summaryInteractor.switchToMainMenuView();
    }
}
