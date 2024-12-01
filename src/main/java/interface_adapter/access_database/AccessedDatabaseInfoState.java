package interface_adapter.access_database;

import entity.Database;
import entity.PlayerQuizDatabase;
import entity.User;

public class AccessedDatabaseInfoState {
    private PlayerQuizDatabase database;
    private String username;

    public PlayerQuizDatabase getDatabase() {
        return database;
    }

    public void setDatabase(PlayerQuizDatabase database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
