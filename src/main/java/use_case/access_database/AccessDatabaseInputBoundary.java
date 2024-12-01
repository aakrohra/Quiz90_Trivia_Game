package use_case.access_database;

import entity.Quiz;

/**
 * Input Boundary for actions which are related to the database.
 */
public interface AccessDatabaseInputBoundary {

    /**
     * Execute the access database use case using relevant input data.
     * @param accessDatabaseInputData the input data
     */
    void execute(AccessDatabaseInputData accessDatabaseInputData);

    /**
     * Switch to main menu view use case.
     */
    void switchToMainMenuView();

    /**
     * Play the selected quiz through the playthrough quiz use case.
     * @param quiz selected quiz object
     */
    void switchToPlaythroughView(Quiz quiz);

    /**
     * Executes a switch to the created question view.
     * @param username the currently logged in user's username
     */
    void switchToCreateQuestionView(String username);

    void updateDatabase(String username);
}
