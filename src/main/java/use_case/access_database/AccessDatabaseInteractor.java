package use_case.access_database;

import java.util.Map;

import entity.PlayerCreatedQuiz;
import entity.PlayerQuizDatabase;
import entity.Quiz;
import entity.User;

/**
 * The interactor class for accessing a user's quiz data from the database.
 * It interacts with a data access object to fetch user-specific quiz data and presents the result
 * via an output boundary.
 */

public class AccessDatabaseInteractor {
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
        final User user = accessDatabaseInputData.getUser();

        if (customQuizDataAccessObject.existsByName(user)) {
            final Map<String, PlayerCreatedQuiz> quizMap = customQuizDataAccessObject.getAllUserQuizzes(user);
            final PlayerQuizDatabase database = new PlayerQuizDatabase(user, quizMap);
            final AccessDatabaseOutputData accessDatabaseOutputData = new AccessDatabaseOutputData(false, database);
            accessDatabasePresenter.prepareSuccessView(accessDatabaseOutputData);
        }
        else {
            accessDatabasePresenter.prepareFailView("There is no user with the username " + user.getName() + ".");
        }
    }
}
