package use_case.access_quiz;

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

        // why is this an issue?
        final String key = AccessQuizInputData.getKey();

        if (customQuizDataAccessObject.existsByKey(key)) {
            final Object customQuiz = customQuizDataAccessObject.getQuizFromKey(key);
            // do something to turn Object customQuiz into a QuizObject.
            Quiz quizObject = null;
            final AccessQuizOutputData accessQuizOutputData = new AccessQuizOutputData(
                    false, "", 0, quizObject);
            accessQuizPresenter.prepareSuccessView(accessQuizOutputData);
        }
        else {
            accessQuizPresenter.prepareFailView("There is no quiz with the key \"" + key + "\".");
        }
    }

    @Override
    public void switchToAccessedQuizInfoView() {
        accessQuizPresenter.switchToAccessedQuizInfoView();
    }
}
