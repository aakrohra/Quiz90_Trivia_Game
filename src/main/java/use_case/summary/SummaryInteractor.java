package use_case.summary;

import entity.Quiz;
import kotlin.Pair;

import java.util.Map;

/**
 * Interactor for the Summary Use Case.
 */
public class SummaryInteractor implements SummaryInputBoundary {

    private final SummaryOutputBoundary summaryPresenter;

    public SummaryInteractor(SummaryOutputBoundary summaryPresenter) {
        this.summaryPresenter = summaryPresenter;
    }

    /**
     * Executes the action to switch to the Summary view.
     */
    @Override
    public void prepareSummaryView(Quiz quiz, int numOfCorrectAnswers, Map<Integer, Pair<String, Boolean>> playerInfo) {
        summaryPresenter.prepareSummaryView(quiz, numOfCorrectAnswers, playerInfo);
    }

    /**
     * Executes the action to switch to the Main Menu view.
     */
    @Override
    public void switchToMainMenuView() {
        System.out.println("Switching to the Main Menu View.");
    }
}
