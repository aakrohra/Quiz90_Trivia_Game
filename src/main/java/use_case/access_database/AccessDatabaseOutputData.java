package use_case.access_database;

import entity.PlayerQuizDatabase;

public class AccessDatabaseOutputData {
    private final boolean useCaseFailed;
    private final PlayerQuizDatabase quizDatabase;
    private final String username;

    public AccessDatabaseOutputData(boolean useCaseFailed, PlayerQuizDatabase quizDatabase, String username) {
        this.useCaseFailed = useCaseFailed;
        this.quizDatabase = quizDatabase;
        this.username = username;
    }

    public PlayerQuizDatabase getQuizDatabase() {
        return quizDatabase;
    }

    public String getUsername() {
        return username;
    }
}
