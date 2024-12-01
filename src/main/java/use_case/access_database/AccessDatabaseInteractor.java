package use_case.access_database;

import java.util.Map;

import entity.RetrievedQuiz;
import entity.PlayerQuizDatabase;

/**
 * The interactor class for accessing a user's quiz data from the database.
 * It interacts with a data access object to fetch user-specific quiz data and presents the result
 * via an output boundary.
 */

public class AccessDatabaseInteractor implements AccessDatabaseInputBoundary{
    private final AccessDatabaseUserDataAccessInterface customQuizDataAccessObject;
    private final AccessDatabaseOutputBoundary accessDatabasePresenter;

    public AccessDatabaseInteractor(AccessDatabaseUserDataAccessInterface customQuizDataAccessObject,
                                    AccessDatabaseOutputBoundary accessDatabasePresenter) {
        this.customQuizDataAccessObject = customQuizDataAccessObject;
        this.accessDatabasePresenter = accessDatabasePresenter;
    }

    /**
     * Executes the logic to retrieve and present the user's quiz data from the database.
     * If the user exists, the quizzes are retrieved and passed to the presenter.
     * If the user does not exist, an error message is sent to the presenter.
     * @param accessDatabaseInputData the input data containing the user information for the query
     */

    public void execute(AccessDatabaseInputData accessDatabaseInputData) {
        final String username = accessDatabaseInputData.getUsername();

        if (customQuizDataAccessObject.existsByName(username)) {
            final Map<String, RetrievedQuiz> quizMap = customQuizDataAccessObject.getAllUserQuizzes(username);
            final PlayerQuizDatabase database = new PlayerQuizDatabase(quizMap);
            final AccessDatabaseOutputData accessDatabaseOutputData =
                    new AccessDatabaseOutputData(false, database, username);

            System.out.println("it works");
            accessDatabasePresenter.prepareSuccessView(accessDatabaseOutputData);
        }
    }

    @Override
    public void switchToMainMenuView() {
        accessDatabasePresenter.switchToMainMenuView();
    }

    @Override
    public void switchToCreateQuestionView(String username) {
        accessDatabasePresenter.switchToCreateQuestionView(username);
    }
}
