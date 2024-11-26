package use_case.access_database;

public interface AccessDatabaseOutputBoundary {

    void prepareSuccessView(AccessDatabaseOutputData data);
    void switchToMainMenuView();
}
