package interface_adapter.create_question;

import use_case.create_question.QuestionCreationOutputBoundary;
import use_case.create_question.QuestionCreationOutputData;

/**
 * The presenter for question creation (custom).
 */
public class QuestionCreationPresenter implements QuestionCreationOutputBoundary {
    private final QuestionCreationViewModel questionCreationViewModel;

    public QuestionCreationPresenter(QuestionCreationViewModel questionCreationViewModel) {
        this.questionCreationViewModel = questionCreationViewModel;
    }

    @Override
    public void prepareSuccessView(QuestionCreationOutputData outputData) {
        final QuestionCreationState currentState = questionCreationViewModel.getState();
        currentState.addToQuestionsSoFar(outputData.getQuestionObject());
        questionCreationViewModel.firePropertyChanged("next");
    }

}
