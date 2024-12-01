package interface_adapter.change_password;

/**
 * The state for the ChangePasswordViewModel.
 */
public class ChangePasswordState {
    private String username = "";
    private String password = "";

    public ChangePasswordState(ChangePasswordState copy) {
        username = copy.username;
        password = copy.password;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public ChangePasswordState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
