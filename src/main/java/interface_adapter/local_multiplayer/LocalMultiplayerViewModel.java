package interface_adapter.local_multiplayer;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Local Multiplayer view.
 */
public class LocalMultiplayerViewModel extends ViewModel<LocalMultiplayerState> {
    public static final String TITLE_LABEL = "Local Multiplayer Quiz Generation!";

    public LocalMultiplayerViewModel() {
        super("local multiplayer");
        setState(new LocalMultiplayerState());
    }
}

