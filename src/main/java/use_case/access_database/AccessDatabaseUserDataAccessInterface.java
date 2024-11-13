package use_case.access_database;

import java.util.Map;

import entity.Quiz;
import entity.User;

/**
 * DAO for the Access Database Use Case.
 */
public interface AccessDatabaseUserDataAccessInterface {

    /**
     * Returns all quiz objects associated with the given user.
     * @param user the given user
     * @return map of objects associated with user
     */
    Map<String, Quiz> getAllUserQuizzes(User user);
}
