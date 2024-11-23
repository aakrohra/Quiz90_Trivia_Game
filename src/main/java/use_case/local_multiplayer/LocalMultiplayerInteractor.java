package use_case.local_multiplayer;

/**
 * Interactor for Local Multiplayer Use Case.
 */
public class LocalMultiplayerInteractor implements LocalMultiplayerInputBoundary {

    private final LocalMultiplayerOutputBoundary localMultiplayerPresenter;

    public LocalMultiplayerInteractor(LocalMultiplayerOutputBoundary localMultiplayerPresenter) {
        this.localMultiplayerPresenter = localMultiplayerPresenter;
    }

    /**
     * Executes the action to switch to the Local Multiplayer view.
     */
    @Override
    public void switchToLocalMultiplayerView() {
        localMultiplayerPresenter.switchToLocalMultiplayerView();
        System.out.println("doing2");
    }
}
