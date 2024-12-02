package use_case.access_database;

import java.util.Map;

import org.json.JSONObject;

import entity.RetrievedQuiz;
import entity.User;

/**
 * DAO for the Access Database Use Case.
 */
public interface AccessDatabaseUserDataAccessInterface {

    /**
     * Returns all quiz objects associated with the given user mapped to their key.
     * @param username the given username
     * @return map with key, quiz pairing
     */
    Map<String, RetrievedQuiz> getAllUserQuizzes(String username);

    /**
     * Gets user info including username, password, and quizzes and returns as a JSONObject.
     * @param user the given user
     * @return username, password, and quizzes as a JSONObject
     * @throws RuntimeException if there is an issue
     */
    JSONObject getUserInfo(User user);

    /**
     * Checks if user exists by username.
     * @param username the given username
     * @return true if user does exist, false otherwise
     */
    boolean existsByName(String username);
}
