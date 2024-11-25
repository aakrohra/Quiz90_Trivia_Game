package interface_adapter.local_multiplayer;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.local_multiplayer.LocalMultiplayerOutputBoundary;

/**
 * Presenter for switching to Local Multiplayer View.
 */
public class LocalMultiplayerPresenter implements LocalMultiplayerOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final LocalMultiplayerViewModel localMultiplayerViewModel;
    private final LoggedInViewModel loggedInViewModel;

    public LocalMultiplayerPresenter(ViewManagerModel viewManagerModel,
                                   LocalMultiplayerViewModel localMultiplayerViewModel,
                                     LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.localMultiplayerViewModel = localMultiplayerViewModel;
        this.loggedInViewModel = loggedInViewModel;
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
}
