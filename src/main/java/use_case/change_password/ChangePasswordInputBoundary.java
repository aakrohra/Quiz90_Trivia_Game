package use_case.change_password;

/**
 * The Change Password Use Case.
 */
public interface ChangePasswordInputBoundary {

    /**
     * Execute the Change Password Use Case.
     * @param changePasswordInputData the input data for this use case
     */
    void execute(ChangePasswordInputData changePasswordInputData);

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
