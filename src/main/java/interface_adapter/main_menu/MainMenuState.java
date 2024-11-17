package interface_adapter.main_menu;

/**
 * The state for the Main Menu View Model.
 */
public class MainMenuState {
    private String username = "";
    private String errorMessage = "";

    public String getUsername() {
        return username;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
