package use_case.local_multiplayer;

/**
 * Output Boundary interface for the Local Multiplayer Use Case.
 */
public interface LocalMultiplayerOutputBoundary {

    // TODO all use cases need both success and fail views
//    /**
//     * Prepares the success view for the Local Multiplayer use case.
//     * @param outputData the output data
//     */
//    void prepareSuccessView(LocalMultiplayerOutputData outputData);
//
//    /**
//     * Prepares the failure view for the Local Multiplayer use case.
//     * @param errorMessage the message to display on error
//     */
//    void prepareFailView(String errorMessage);

    /**
     * Prepares the view to switch to the Local Multiplayer screen.
     */
    void switchToLocalMultiplayerView();
}
