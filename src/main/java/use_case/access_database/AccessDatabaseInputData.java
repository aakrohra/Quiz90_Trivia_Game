package use_case.access_database;

import entity.User;

public class AccessDatabaseInputData {

    private final User user;

    public AccessDatabaseInputData(User user) {
        this.user = user;
    }

    User getUser() {
        return user;
    }
}
