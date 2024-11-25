package interface_adapter.access_database;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.quiz_generation.QuizGenerationViewModel;
import use_case.access_database.AccessDatabaseOutputBoundary;
import use_case.access_database.AccessDatabaseOutputData;
import view.QuizDatabaseView;
import view.ViewManager;

public class AccessDatabasePresenter implements AccessDatabaseOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel;
    private final LoggedInViewModel loggedInViewModel;

    public AccessDatabasePresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel, AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.accessedDatabaseInfoViewModel = accessedDatabaseInfoViewModel;
    }

    @Override
    public void switchToMainMenuView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(AccessDatabaseOutputData accessDatabaseOutputData) {
        System.out.println("prepare successview");
        AccessedDatabaseInfoState accessedDatabaseInfoState = accessedDatabaseInfoViewModel.getState();

        accessedDatabaseInfoState.setDatabase(accessDatabaseOutputData.getQuizDatabase());
        this.accessedDatabaseInfoViewModel.setState(accessedDatabaseInfoState);
        this.accessedDatabaseInfoViewModel.firePropertyChanged();

        this.viewManagerModel.setState(accessedDatabaseInfoViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
