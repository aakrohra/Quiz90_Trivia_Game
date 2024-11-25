package interface_adapter.change_password;

import interface_adapter.ViewModel;

public class ChangePasswordViewModel extends ViewModel<ChangePasswordState> {
    public static final String TITLE_LABEL = "Change Password";

    public ChangePasswordViewModel() {
        super("Change Password");
        setState(new ChangePasswordState());
    }
}
