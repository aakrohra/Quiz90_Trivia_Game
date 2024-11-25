package use_case.access_database;

public interface AccessDatabaseInputBoundary {
    void execute(AccessDatabaseInputData accessDatabaseInputData);

    void switchToMainMenuView();
}
