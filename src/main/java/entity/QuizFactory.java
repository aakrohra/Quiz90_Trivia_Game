package entity;

import org.json.JSONObject;

/**
 * Factory for creating Quizzes.
 */
public interface QuizFactory {

    /**
     * Creates a new Quiz object.
     * @param quizData the quiz data as a JSONObject
     * @param key the key of the quiz
     * @return the new Quiz object
     */
    Quiz create(JSONObject quizData, String key);
}
