package interface_adapter.local_multiplayer_summary;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Summary View.
 */
public class LocalMultiplayerSummaryViewModel extends ViewModel<LocalMultiplayerSummaryState> {

    public LocalMultiplayerSummaryViewModel() {
        super("local multiplayer summary");
        setState(new LocalMultiplayerSummaryState());
    }
}
