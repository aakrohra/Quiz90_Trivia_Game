package interface_adapter.access_quiz;

import entity.PlayerCreatedQuiz;
import use_case.access_quiz.AccessQuizInputBoundary;
import use_case.access_quiz.AccessQuizInputData;

/**
 * The controller for the Access Quiz Use Case.
 */
public class AccessQuizController {

    private final AccessQuizInputBoundary accessQuizUseCaseInteractor;

    public AccessQuizController(AccessQuizInputBoundary accessQuizUseCaseInteractor) {
        this.accessQuizUseCaseInteractor = accessQuizUseCaseInteractor;
    }

    /**
     * Executes the Access Quiz from Key Use Case.
     * @param key the created quiz key
     */
    public void accessQuizFromKey(String key) {
        final AccessQuizInputData accessQuizInputData = new AccessQuizInputData(key);

        accessQuizUseCaseInteractor.execute(accessQuizInputData);
    }

    /**
     * Executes the Switch to Logged In View Use Case.
     */
    public void switchToLoggedInView() {
        accessQuizUseCaseInteractor.switchToLoggedInView();
    }

    /**
     * Executes the Play Accessed Quiz Use Case.
     * @param quizObject the quiz object passed in
     */
    public void playAccessedQuiz(PlayerCreatedQuiz quizObject) {
        accessQuizUseCaseInteractor.playAccessedQuiz(quizObject);
    }
}
