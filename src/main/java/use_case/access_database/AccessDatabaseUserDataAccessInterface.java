package use_case.access_database;

import java.util.Map;

import entity.Quiz;
import entity.User;
import org.json.JSONObject;

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

    /**
     * Gets user info including username, password, and quizzes and returns as a JSONObject.
     * @param user the given user
     * @return username, password, and quizzes as a JSONObject
     * @throws RuntimeException if there is an issue
     */
    JSONObject getUserInfo(User user);
}
