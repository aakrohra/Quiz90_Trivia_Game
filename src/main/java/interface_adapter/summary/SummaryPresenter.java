package interface_adapter.summary;

import entity.Quiz;
import interface_adapter.ViewManagerModel;
import kotlin.Pair;
import use_case.summary.SummaryOutputBoundary;

import java.util.Map;

/**
 * The Presenter for handling the Summary Use Case.
 */
public class SummaryPresenter implements SummaryOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final SummaryViewModel summaryViewModel;

    public SummaryPresenter(ViewManagerModel viewManagerModel, SummaryViewModel summaryViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.summaryViewModel = summaryViewModel;
    }

    /**
     * Prepares the view to display the summary screen.
     */
    @Override
    public void prepareSummaryView(Quiz quiz, int numOfCorrectAnswers, Map<Integer, Pair<String, Boolean>> playerInfo) {
        final SummaryState summaryState = summaryViewModel.getState();
        summaryState.setQuiz(quiz);
        summaryState.setPlayerInfo(playerInfo);
        summaryState.setNumberOfCorrectAnswers(numOfCorrectAnswers);
        summaryViewModel.setState(summaryState);
        summaryViewModel.firePropertyChanged();

        // Switch to Summary View
        viewManagerModel.setState(summaryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the view to switch to the Main Menu screen.
     */
    @Override
    public void switchToMainMenuView() {
        System.out.println("Switching to Main Menu View...");
    }
}