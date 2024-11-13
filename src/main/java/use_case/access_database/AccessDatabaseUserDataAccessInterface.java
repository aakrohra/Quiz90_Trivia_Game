package use_case.access_database;

import java.util.List;

import entity.Quiz;
import entity.User;

/**
 * DAO for the Access Database Use Case.
 */
public interface AccessDatabaseUserDataAccessInterface {

    /**
     * Returns all quiz objects associated with the given user.
     * @param user the given user
     * @return list of objects associated with user
     */
    List<Quiz> getAllUserQuizzes(User user);
}
