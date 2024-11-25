package interface_adapter.access_database;

import entity.Database;
import entity.User;

public class AccessedDatabaseInfoState {
    private Database database;

    private Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
