package entity;

import org.json.JSONObject;

/**
 * Factory for creating Questions.
 */
public interface QuestionFactory {
    /**
     * Creates a new Question.
     * @param questionData the data for the question as a JSONObject
     * @return the question object
     */
    Question create(JSONObject questionData);

}
