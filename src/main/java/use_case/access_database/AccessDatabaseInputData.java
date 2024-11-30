package use_case.access_database;

/**
 * The input data for the access database use case.
 */
public class AccessDatabaseInputData {

    private final String username;

    public AccessDatabaseInputData(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }
}
