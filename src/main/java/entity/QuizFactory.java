package entity;

import org.json.JSONObject;

/**
 * Factory for creating Quizzes.
 */
public interface QuizFactory {

    /**
     * Creates a new Quiz object.
     * @param quizData the quiz data as a JSONObject
     * @return the new Quiz object
     */
    Quiz create(JSONObject quizData);
}
