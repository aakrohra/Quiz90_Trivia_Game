package interface_adapter.logged_in;

/**
 * The State information representing the logged-in user.
 */
public class LoggedInState {
    private String username = "";
    private String password = "";
    private String quizKey = "";
    private String quizKeyError;
    private String databaseError;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getQuizKey() {
        return quizKey;
    }

    public void setQuizKey(String quizKey) {
        this.quizKey = quizKey;
    }

    public void setQuizKeyError(String quizKeyError) {
        this.quizKeyError = quizKeyError;
    }

    public String getQuizKeyError() {
        return this.quizKeyError;
    }

    public String getDatabaseError() {
        return databaseError;
    }

    public void setDatabaseError(String databaseError) {
        this.databaseError = databaseError;
    }
}
