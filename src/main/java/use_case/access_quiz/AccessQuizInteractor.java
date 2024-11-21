package use_case.access_quiz;

import org.json.JSONObject;

import entity.PlayerCreatedQuiz;
import entity.PlayerCreatedQuizFactory;

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
            System.out.println("key exists");
        }
        else {
            System.out.println("key not exists");
            accessQuizPresenter.prepareFailView("There is no quiz with the key \"" + key + "\".");
        }
    }
}
