package use_case.access_database;

import java.util.Map;

import org.json.JSONObject;

import entity.PlayerCreatedQuiz;
import entity.User;

/**
 * DAO for the Access Database Use Case.
 */
public interface AccessDatabaseUserDataAccessInterface {

    /**
     * Returns all quiz objects associated with the given user mapped to their key.
     * @param user the given user
     * @return map with key, quiz pairing
     */
    Map<String, PlayerCreatedQuiz> getAllUserQuizzes(String username);

    /**
     * Gets user info including username, password, and quizzes and returns as a JSONObject.
     * @param user the given user
     * @return username, password, and quizzes as a JSONObject
     * @throws RuntimeException if there is an issue
     */
    JSONObject getUserInfo(User user);

    boolean existsByName(String username);
}
