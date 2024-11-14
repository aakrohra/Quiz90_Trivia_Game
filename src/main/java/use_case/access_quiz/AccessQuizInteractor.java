package use_case.access_quiz;

import entity.PlayerCreatedQuiz;
import entity.PlayerCreatedQuizFactory;
import org.json.JSONObject;

import entity.Quiz;

/**
 * The Access Quiz Interactor.
 */
public class AccessQuizInteractor implements AccessQuizInputBoundary {

    private final AccessQuizUserDataAccessInterface customQuizDataAccessObject;
    private final AccessQuizOutputBoundary accessQuizPresenter;

    public AccessQuizInteractor(AccessQuizUserDataAccessInterface customQuizDataAccessObject,
                                AccessQuizOutputBoundary accessQuizPresenter) {
        this.customQuizDataAccessObject = customQuizDataAccessObject;
        this.accessQuizPresenter = accessQuizPresenter;
    }

    @Override
    public void execute(AccessQuizInputData accessQuizInputData) {

        final String key = accessQuizInputData.getKey();

        if (customQuizDataAccessObject.existsByKey(key)) {
            final PlayerCreatedQuizFactory quizFactory = new PlayerCreatedQuizFactory();
            final JSONObject quizData = customQuizDataAccessObject.getQuizFromKey(key);
            final PlayerCreatedQuiz quizObject = quizFactory.create(quizData, key);

            final AccessQuizOutputData accessQuizOutputData = new AccessQuizOutputData(
                    false, quizObject);

            accessQuizPresenter.prepareSuccessView(accessQuizOutputData);
        }
        else {
            accessQuizPresenter.prepareFailView("There is no quiz with the key \"" + key + "\".");
        }
    }
}
