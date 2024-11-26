package use_case.change_password;

/**
 * The output boundary for the Change Password Use Case.
 */
public interface ChangePasswordOutputBoundary {
    /**
     * Prepares the success view for the Change Password Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ChangePasswordOutputData outputData);

    /**
     * Prepares the failure view for the Change Password Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches the view to the Change Password screen.
     * @param changePasswordInputData the input data required to switch to the Change Password screen
     */
    void switchToChangePasswordView(ChangePasswordInputData changePasswordInputData);

    /**
     * Switches the view to the Main Menu (logged in) screen.
     */
    void switchToMainMenuView();
}
