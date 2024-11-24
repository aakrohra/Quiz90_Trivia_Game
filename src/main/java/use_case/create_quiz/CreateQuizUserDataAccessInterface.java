package use_case.create_quiz;

import org.json.JSONObject;

import entity.User;

/**
 * DAO for the Creating Quiz use case.
 */
public interface CreateQuizUserDataAccessInterface {

    /**
     * Adds quiz to the GradeAPI database to the given user and returns key of new quiz.
     * @param quizObject the given quiz
     * @param user the given user
     * @return key of quiz added to the database
     */
    String addQuiz(JSONObject quizObject, User user);
}
