package interface_adapter.local_multiplayer_playthrough;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the LocalMultiplayerPlaythrough View.
 */
public class LocalMultiplayerPlaythroughViewModel extends ViewModel<LocalMultiplayerPlaythroughState> {

    public LocalMultiplayerPlaythroughViewModel() {
        super("local multiplayer playthrough");
        setState(new LocalMultiplayerPlaythroughState());
    }

}
