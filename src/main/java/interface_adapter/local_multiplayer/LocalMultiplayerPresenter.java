package interface_adapter.local_multiplayer;

import java.util.Map;

import entity.Quiz;
import entity.TriviaQuiz;
import interface_adapter.ViewManagerModel;
import interface_adapter.local_multiplayer_playthrough.LocalMultiplayerPlaythroughState;
import interface_adapter.local_multiplayer_playthrough.LocalMultiplayerPlaythroughViewModel;
import interface_adapter.local_multiplayer_summary.LocalMultiplayerSummaryState;
import interface_adapter.local_multiplayer_summary.LocalMultiplayerSummaryViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import kotlin.Pair;
import use_case.local_multiplayer.LocalMultiplayerOutputBoundary;

/**
 * Presenter for switching to Local Multiplayer View.
 */
public class LocalMultiplayerPresenter implements LocalMultiplayerOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final LocalMultiplayerViewModel localMultiplayerViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final LocalMultiplayerPlaythroughViewModel localMultiplayerPlaythroughViewModel;
    private final LocalMultiplayerSummaryViewModel localMultiplayerSummaryViewModel;

    public LocalMultiplayerPresenter(ViewManagerModel viewManagerModel,
                                     LocalMultiplayerViewModel localMultiplayerViewModel,
                                     LoggedInViewModel loggedInViewModel,
                                     LocalMultiplayerPlaythroughViewModel localMultiplayerPlaythroughViewModel,
                                     LocalMultiplayerSummaryViewModel localMultiplayerSummaryViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.localMultiplayerViewModel = localMultiplayerViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.localMultiplayerPlaythroughViewModel = localMultiplayerPlaythroughViewModel;
        this.localMultiplayerSummaryViewModel = localMultiplayerSummaryViewModel;
    }

    /**
     * Presents the Quiz to be played.
     */
    @Override
    public void prepareQuiz(TriviaQuiz triviaQuiz) {
        final LocalMultiplayerPlaythroughState localMultiplayerPlaythroughState =
                localMultiplayerPlaythroughViewModel.getState();
        localMultiplayerPlaythroughState.setQuiz(triviaQuiz);
        localMultiplayerPlaythroughViewModel.setState(localMultiplayerPlaythroughState);

        localMultiplayerPlaythroughViewModel.firePropertyChanged();

        // Switch to the Playthrough View
        viewManagerModel.setState(localMultiplayerPlaythroughViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the view to switch to the Local Multiplayer screen.
     */
    @Override
    public void switchToLocalMultiplayerView() {
        // Update the state in the ViewManagerModel
        viewManagerModel.setState(localMultiplayerViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the view to switch to the Main Menu screen.
     */
    @Override
    public void switchToMainMenuView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the fail view.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        localMultiplayerViewModel.getState().setError(errorMessage);
        localMultiplayerViewModel.firePropertyChanged();
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
        final LocalMultiplayerSummaryState localMultiplayerSummaryState = localMultiplayerSummaryViewModel.getState();
        localMultiplayerSummaryState.setQuiz(quiz);
        localMultiplayerSummaryState.setPlayerOneInfo(playerOneInfo);
        localMultiplayerSummaryState.setPlayerTwoInfo(playerTwoInfo);
        localMultiplayerSummaryState.setNumMapCorrect(numMapCorrect);
        localMultiplayerSummaryViewModel.setState(localMultiplayerSummaryState);
        localMultiplayerSummaryViewModel.firePropertyChanged();

        // Switch to Summary View
        viewManagerModel.setState(localMultiplayerSummaryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
