package use_case.access_quiz;

import entity.Quiz;

/**
 * The Access Quiz Interactor.
 */
public class AccessQuizInteractor implements AccessQuizInputBoundary {

    private final AccessQuizUserDataAccessInterface customQuizDataAccessObject;

    public AccessQuizInteractor(AccessQuizUserDataAccessInterface customQuizDataAccessObject) {
        this.customQuizDataAccessObject = customQuizDataAccessObject;
    }

    @Override
    public void execute(AccessQuizInputData accessQuizInputData) {

        final String key = AccessQuizInputData.getKey();

        if (customQuizDataAccessObject.existsByKey(key)) {
            final Object customQuiz = customQuizDataAccessObject.getQuizFromKey(key);
        }
        else {
            // fail view in output boundary??
        }

    }

    @Override
    public void switchToAccessedQuizInfoView() {
        accessQuizPresenter.switchToAccessedQuizInfoView();
    }
}
