package interface_adapter.create_quiz;

import interface_adapter.ViewManagerModel;
import use_case.create_quiz.QuizCreationOutputBoundary;
import use_case.create_quiz.QuizCreationOutputData;

public class QuizCreationPresenter implements QuizCreationOutputBoundary {

    private final QuizCreationViewModel quizCreationViewModel;
    private final ViewManagerModel viewManagerModel;

    public QuizCreationPresenter(ViewManagerModel viewManagerModel,
                                 QuizCreationViewModel quizCreationViewModel) {
        this.quizCreationViewModel = quizCreationViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(QuizCreationOutputData outputData) {
        final QuizCreationState currentState = quizCreationViewModel.getState();
        currentState.setQuizObject(outputData.getQuizObject());
        currentState.setKey(outputData.getKey());
        quizCreationViewModel.setState(currentState);
        quizCreationViewModel.firePropertyChanged();

        viewManagerModel.setState(quizCreationViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
