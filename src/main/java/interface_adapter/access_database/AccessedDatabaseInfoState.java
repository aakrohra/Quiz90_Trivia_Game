package interface_adapter.access_database;

import entity.Database;
import entity.User;

public class AccessedDatabaseInfoState {
    private Database database;

    public AccessedDatabaseInfoState(Database database) {
        this.database = database;
    }
}
