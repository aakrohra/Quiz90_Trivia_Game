package use_case.access_quiz;

import org.json.JSONObject;

/**
 * DAO for the Access Quiz Use Case.
 */
public interface AccessQuizUserDataAccessInterface {

    /**
     * Check if the given key points to an existing created quiz.
     * @param key the given key
     * @return true if a quiz with the key exists, false otherwise
     */
    boolean existsByKey(String key);

    /**
     * Returns the quiz data from the given key as a JSONObject.
     * @param key the given key
     * @return the quiz associated with the key as a JSONObject
     */
    JSONObject getQuizFromKey(String key);
}
