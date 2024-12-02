package use_case.access_database;

import java.util.Map;

import entity.PlayerQuizDatabase;
import entity.Quiz;
import entity.RetrievedQuiz;

/**
 * The interactor class for accessing a user's quiz data from the database.
 * It interacts with a data access object to fetch user-specific quiz data and presents the result
 * via an output boundary.
 */
public class AccessDatabaseInteractor implements AccessDatabaseInputBoundary {
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
     * @param accessDatabaseInputData the input data containing the user information for the query
     */
    public void execute(AccessDatabaseInputData accessDatabaseInputData) {
        final String username = accessDatabaseInputData.getUsername();

        if (customQuizDataAccessObject.existsByName(username)) {
            final Map<String, RetrievedQuiz> quizMap = customQuizDataAccessObject.getAllUserQuizzes(username);
            final PlayerQuizDatabase database = new PlayerQuizDatabase(quizMap);
            final AccessDatabaseOutputData accessDatabaseOutputData =
                    new AccessDatabaseOutputData(false, database, username);

            accessDatabasePresenter.prepareSuccessView(accessDatabaseOutputData);
        }
        else {
            accessDatabasePresenter.prepareFailView("There is no user named: " + username);
        }
    }

    @Override
    public void switchToMainMenuView() {
        accessDatabasePresenter.switchToMainMenuView();
    }

    /**
     * Executes the logic to playthrough use case view using the selected quiz object.
     * @param quiz selected quiz object
     */
    public void switchToPlaythroughView(Quiz quiz) {
        accessDatabasePresenter.preparePlaythroughView(quiz);
    }

    @Override
    public void switchToCreateQuestionView(String username) {
        accessDatabasePresenter.switchToCreateQuestionView(username);
    }

    /**
     * Executes the logic to update the database.
     */
    @Override
    public void updateDatabase(String username) {
        final AccessDatabaseInputData accessDatabaseInputData = new AccessDatabaseInputData(username);
        execute(accessDatabaseInputData);
    }
}
