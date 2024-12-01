package use_case.access_quiz;

import entity.RetrievedQuiz;

/**
 * Input Boundary for actions which are related to accessing a quiz.
 */
public interface AccessQuizInputBoundary {

    /**
     * Executes the access quiz use case using the relevant input data.
     * @param accessQuizInputData the input data
     */
    void execute(AccessQuizInputData accessQuizInputData);

    /**
     * Switch to Logged In View use case.
     */
    void switchToLoggedInView();

    /**
     * Play Accessed Quiz use case.
     * @param quizObject the quiz object
     */
    void playAccessedQuiz(RetrievedQuiz quizObject);
}
