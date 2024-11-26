package use_case.access_database;

import entity.User;

public class AccessDatabaseInputData {

    private final String username;

    public AccessDatabaseInputData(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }
}
