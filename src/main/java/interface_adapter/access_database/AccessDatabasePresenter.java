package interface_adapter.access_database;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.access_database.AccessDatabaseOutputBoundary;
import use_case.access_database.AccessDatabaseOutputData;
import view.ViewManager;

public class AccessDatabasePresenter implements AccessDatabaseOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel;
    private final LoggedInViewModel loggedInViewModel;

    public AccessDatabasePresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.accessedDatabaseInfoViewModel = new AccessedDatabaseInfoViewModel();
    }

    @Override
    public void switchToMainMenuView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
