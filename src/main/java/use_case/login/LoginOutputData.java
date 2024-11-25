package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String password;
    private final String username;
    private final boolean useCaseFailed;

    public LoginOutputData(String password, String username, boolean useCaseFailed) {
        this.password = password;
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
