package use_case.access_database;

import entity.Quiz;

/**
 * The output boundary for the Access database use case.
 */
public interface AccessDatabaseOutputBoundary {

    /**
     * Prepares sucess view for the access database use case.
     * @param data the output data
     */
    void prepareSuccessView(AccessDatabaseOutputData data);

    /**
     * Prepares the main menu view for returning to logged in view use case.
     */
    void switchToMainMenuView();

    /**
     * Prepares the playthrough view for playing a selected quiz.
     * @param quiz the selected quiz
     */
    void preparePlaythroughView(Quiz quiz);
    void switchToCreateQuestionView(String username);
}
