package interface_adapter.local_multiplayer;

import use_case.local_multiplayer.LocalMultiplayerInputBoundary;

/**
 * Controller for the Local Multiplayer Use Case.
 */
public class LocalMultiplayerController {

    private final LocalMultiplayerInputBoundary localMultiplayerInputBoundary;

    public LocalMultiplayerController(LocalMultiplayerInputBoundary localMultiplayerInteractor) {
        this.localMultiplayerInputBoundary = localMultiplayerInteractor;
    }

    /**
     * Executes the action to switch to the Local Multiplayer view.
     */
    public void switchToLocalMultiplayerView() {
        localMultiplayerInputBoundary.switchToLocalMultiplayerView();
    }

    /**
     * Executes the action to switch to the Main Menu view.
     */
    public void switchToMainMenuView() {
        localMultiplayerInputBoundary.switchToMainMenuView();
    }
}
