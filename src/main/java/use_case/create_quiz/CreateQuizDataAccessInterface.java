package use_case.create_quiz;

import entity.CreatedQuiz;
import org.json.JSONObject;

import entity.User;

/**
 * DAO for the Creating Quiz use case.
 */
public interface CreateQuizDataAccessInterface {

    /**
     * Adds quiz to the GradeAPI database to the given user and returns key of new quiz.
     * @param quizObject the given quiz
     * @param user the given user
     * @return key of quiz added to the database
     */
    String addQuiz(JSONObject quizObject, User user);

    /**
     * Returns a JSONObject representing a quiz given a player created quiz object.
     * @param quiz the given quiz
     * @return JSONObject of the quiz object in the following format:
     *     {
     *       "title": "title1",
     *       "questions": [
     *         {
     *           "questionText": "qqqqq",
     *           "options": ["o1", "o2", "o3", "o4"],
     *           "correct": "o2"
     *         },
     *         ...
     *       ]
     *     }
     */
    JSONObject quizObjectToJSONObject(CreatedQuiz quiz);
}
