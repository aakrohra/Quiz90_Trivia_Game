package use_case.access_database;

public interface AccessDatabaseInputBoundary {

    void execute(AccessDatabaseInputData accessDatabaseInputData);

    void switchToMainMenuView();

    /**
     * Executes a switch to the created question view.
     * @param username the currently logged in user's username
     */
    void switchToCreateQuestionView(String username);
}
