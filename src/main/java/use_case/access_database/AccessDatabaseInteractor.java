package use_case.access_database;

import entity.PlayerCreatedQuiz;
import entity.PlayerQuizDatabase;
import entity.Quiz;
import entity.User;

import java.util.Map;

public class AccessDatabaseInteractor {
    private final AccessDatabaseUserDataAccessInterface customQuizDataAccessObject;
    private final AccessDatabaseOutputBoundary accessDatabasePresenter;

    public AccessDatabaseInteractor(AccessDatabaseUserDataAccessInterface customQuizDataAccessObject,
                                    AccessDatabaseOutputBoundary accessDatabasePresenter) {
        this.customQuizDataAccessObject = customQuizDataAccessObject;
        this.accessDatabasePresenter = accessDatabasePresenter;
    }

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
