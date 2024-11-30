package interface_adapter.access_database;

import entity.Quiz;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.playthrough.PlaythroughState;
import use_case.access_database.AccessDatabaseOutputBoundary;
import use_case.access_database.AccessDatabaseOutputData;
import interface_adapter.playthrough.PlaythroughViewModel;

/**
 * The presenter for the database use case.
 */
public class AccessDatabasePresenter implements AccessDatabaseOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final PlaythroughViewModel playthroughViewModel;

    public AccessDatabasePresenter(ViewManagerModel viewManagerModel,
                                   LoggedInViewModel loggedInViewModel,
                                   AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel,
                                   PlaythroughViewModel playthroughViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.accessedDatabaseInfoViewModel = accessedDatabaseInfoViewModel;
        this.playthroughViewModel = playthroughViewModel;
    }

    @Override
    public void switchToMainMenuView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void preparePlaythroughView(Quiz quiz) {
        final PlaythroughState playthroughState = playthroughViewModel.getState();
        playthroughState.setQuiz(quiz);
        playthroughViewModel.setState(playthroughState);
        playthroughViewModel.firePropertyChanged();

        viewManagerModel.setState(playthroughViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(AccessDatabaseOutputData accessDatabaseOutputData) {
        final AccessedDatabaseInfoState accessedDatabaseInfoState = accessedDatabaseInfoViewModel.getState();

        accessedDatabaseInfoState.setDatabase(accessDatabaseOutputData.getQuizDatabase());

        this.accessedDatabaseInfoViewModel.setState(accessedDatabaseInfoState);
        this.accessedDatabaseInfoViewModel.firePropertyChanged();

        this.viewManagerModel.setState(accessedDatabaseInfoViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
