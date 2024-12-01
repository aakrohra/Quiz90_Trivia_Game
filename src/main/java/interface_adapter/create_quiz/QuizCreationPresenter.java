package interface_adapter.create_quiz;

import interface_adapter.ViewManagerModel;
import interface_adapter.access_database.AccessedDatabaseInfoViewModel;
import use_case.create_quiz.QuizCreationOutputBoundary;
import use_case.create_quiz.QuizCreationOutputData;

public class QuizCreationPresenter implements QuizCreationOutputBoundary {

    private final QuizCreationViewModel quizCreationViewModel;
    private final ViewManagerModel viewManagerModel;
    private final AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel;

    public QuizCreationPresenter(ViewManagerModel viewManagerModel,
                                 QuizCreationViewModel quizCreationViewModel,
                                 AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel) {
        this.quizCreationViewModel = quizCreationViewModel;
        this.viewManagerModel = viewManagerModel;
        this.accessedDatabaseInfoViewModel = accessedDatabaseInfoViewModel;
    }

    @Override
    public void prepareSuccessView(QuizCreationOutputData outputData) {
        final QuizCreationState currentState = quizCreationViewModel.getState();
        currentState.setQuizObject(outputData.getQuizObject());
        currentState.setKey(outputData.getKey());
        quizCreationViewModel.setState(currentState);
        quizCreationViewModel.firePropertyChanged();

        accessedDatabaseInfoViewModel.firePropertyChanged();
        viewManagerModel.setState(accessedDatabaseInfoViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
