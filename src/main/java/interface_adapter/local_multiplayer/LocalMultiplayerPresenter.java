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
        localMultiplayerViewModel.getState().setError(null);
        final LocalMultiplayerPlaythroughState localMultiplayerPlaythroughState =
                localMultiplayerPlaythroughViewModel.getState();

        // initialize the playthrough state
        localMultiplayerPlaythroughState.clearPlayerOneInfo();
        localMultiplayerPlaythroughState.clearPlayerTwoInfo();
        localMultiplayerPlaythroughState.setCurrentPlayerIsOne(true);
        localMultiplayerPlaythroughState.setCurrentQuestionIndex(0);
        final Integer[] tempNumMap = {0, 0};
        localMultiplayerPlaythroughState.setNumMapCorrect(tempNumMap);
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
     * Executes the action to go to the next question in the quiz.
     * @param state The local multiplayer playthrough state.
     */
    @Override
    public void nextQuestion(LocalMultiplayerPlaythroughState state) {
        if (state.getCurrentQuestionIndex() == state.getQuiz().getQuestions().size() - 1) {
            final LocalMultiplayerSummaryState localMultiplayerSummaryState =
                    localMultiplayerSummaryViewModel.getState();

            // update summary state
            localMultiplayerSummaryState.setQuiz(state.getQuiz());
            localMultiplayerSummaryState.setPlayerOneInfo(state.getPlayerOneInfo());
            localMultiplayerSummaryState.setPlayerTwoInfo(state.getPlayerTwoInfo());
            localMultiplayerSummaryState.setNumMapCorrect(state.getNumMapCorrect());

            localMultiplayerSummaryViewModel.setState(localMultiplayerSummaryState);
            localMultiplayerSummaryViewModel.firePropertyChanged();

            // Switch to Summary View
            viewManagerModel.setState(localMultiplayerSummaryViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
        }

        // continue to next question
        else {
            state.setCurrentQuestionIndex(state.getCurrentQuestionIndex() + 1);
            state.setCurrentPlayerIsOne(!state.getCurrentPlayerIsOne());
            this.localMultiplayerPlaythroughViewModel.firePropertyChanged();
        }
    }

}
