package interface_adapter.local_multiplayer;

import entity.TriviaQuiz;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.playthrough.PlaythroughState;
import interface_adapter.playthrough.PlaythroughViewModel;
import use_case.local_multiplayer.LocalMultiplayerOutputBoundary;

/**
 * Presenter for switching to Local Multiplayer View.
 */
public class LocalMultiplayerPresenter implements LocalMultiplayerOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final LocalMultiplayerViewModel localMultiplayerViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final PlaythroughViewModel playthroughViewModel;

    public LocalMultiplayerPresenter(ViewManagerModel viewManagerModel,
                                     LocalMultiplayerViewModel localMultiplayerViewModel,
                                     LoggedInViewModel loggedInViewModel,
                                     PlaythroughViewModel playthroughViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.localMultiplayerViewModel = localMultiplayerViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.playthroughViewModel = playthroughViewModel;
    }

    /**
     * Presents the Quiz to be played.
     */
    @Override
    public void prepareQuiz(TriviaQuiz triviaQuiz) {
        final PlaythroughState playthroughState = playthroughViewModel.getState();
        playthroughState.setQuiz(triviaQuiz);
        playthroughViewModel.setState(playthroughState);

        playthroughViewModel.firePropertyChanged();

        // Switch to the Playthrough View
        viewManagerModel.setState(playthroughViewModel.getViewName());
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
    public void prepareFailView() {
        // TODO: implement prepareFailView similar to how its done for QuizGeneration
        // Currently when the getTrivia method throws an exception prepareFailView just prints to the console
        // Take a look at QuizGenerationPresenter and the PropertyChange method in the QuizGenerationView
        System.out.println("Not enough questions");
    }
}
