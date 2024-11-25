package interface_adapter.change_password;

import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInputData;

/**
 * Controller for the Change Password Use Case.
 */
public class ChangePasswordController {
    private final ChangePasswordInputBoundary userChangePasswordUseCaseInteractor;

    public ChangePasswordController(ChangePasswordInputBoundary userChangePasswordUseCaseInteractor) {
        this.userChangePasswordUseCaseInteractor = userChangePasswordUseCaseInteractor;
    }

    /**
     * Executes the Change Password Use Case.
     * @param password the new password
     * @param username the user whose password to change
     */
    public void execute(String password, String username) {
        final ChangePasswordInputData changePasswordInputData = new ChangePasswordInputData(username, password);

        userChangePasswordUseCaseInteractor.execute(changePasswordInputData);
    }

    /**
     * Switches the view to the Change Password screen.
     * @param password the current password of the user
     * @param username the username of the user
     */
    public void switchToPasswordView(String password, String username) {
        final ChangePasswordInputData inputData = new ChangePasswordInputData(password, username);
        userChangePasswordUseCaseInteractor.switchToChangePasswordView(inputData);
    }

    /**
     * Executes the action to switch to the Main Menu view.
     */
    public void switchToMainMenuView() {
        userChangePasswordUseCaseInteractor.switchToMainMenuView();
    }
}
