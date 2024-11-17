package interface_adapter.main_menu;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Main Menu View.
 */
public class MainMenuViewModel extends ViewModel<MainMenuState> {

    public MainMenuViewModel() {
        super("logged in");
        setState(new MainMenuState());
    }

    /**
     * Updates the username in the state.
     * @param username the current user's username.
     */
    public void updateUsername(String username) {
        MainMenuState currentState = getState();
        currentState.setUsername(username);
        setState(currentState);
        firePropertyChanged("state");
    }

    /**
     * Updates the error message in the state.
     * @param errorMessage the error message to display.
     */
    public void updateErrorMessage(String errorMessage) {
        MainMenuState currentState = getState();
        currentState.setErrorMessage(errorMessage);
        setState(currentState);
        firePropertyChanged("state");
    }
}
