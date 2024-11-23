package interface_adapter.local_multiplayer;

import interface_adapter.ViewManagerModel;
import use_case.local_multiplayer.LocalMultiplayerOutputBoundary;

/**
 * Presenter for switching to Local Multiplayer View.
 */
public class LocalMultiplayerPresenter implements LocalMultiplayerOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final LocalMultiplayerViewModel localMultiplayerViewModel;

    public LocalMultiplayerPresenter(ViewManagerModel viewManagerModel,
                                   LocalMultiplayerViewModel localMultiplayerViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.localMultiplayerViewModel = localMultiplayerViewModel;
    }

    /**
     * Prepares the view to switch to the Local Multiplayer screen.
     */
    @Override
    public void switchToLocalMultiplayerView() {
        System.out.println("Switching to view");

        // Update the state in the ViewManagerModel
        viewManagerModel.setState(localMultiplayerViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
