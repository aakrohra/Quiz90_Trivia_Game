package interface_adapter.access_database;

import entity.Database;
import entity.PlayerQuizDatabase;
import entity.User;

public class AccessedDatabaseInfoState {
    private PlayerQuizDatabase database;

    public PlayerQuizDatabase getDatabase() {
        return database;
    }

    public void setDatabase(PlayerQuizDatabase database) {
        this.database = database;
    }
}
