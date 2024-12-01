package interface_adapter.access_database;

import entity.Database;
import entity.User;

public class AccessedDatabaseInfoState {
    private Database database;
    private String username;

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
