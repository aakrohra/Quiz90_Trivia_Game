package use_case.access_quiz;

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
     * Returns the quiz data from the given key as an Object.
     * @param key the given key
     * @return the quiz associated with the key as an Object
     */
    // should this be the implemented TriviaQuiz instead?
    Object getQuizFromKey(String key);
}
