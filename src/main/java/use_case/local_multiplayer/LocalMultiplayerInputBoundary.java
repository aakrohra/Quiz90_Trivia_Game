package use_case.local_multiplayer;

/**
 * Input Boundary for actions related to local multiplayer.
 */
public interface LocalMultiplayerInputBoundary {

    /**
     * Executes action to switch to Local Multiplayer view.
     */
    void switchToLocalMultiplayerView();

    /**
     * Executes the action to switch to the Main Menu view.
     */
    void switchToMainMenuView();
}
