package interface_adapter.create_quiz;

import interface_adapter.ViewManagerModel;
import interface_adapter.access_database.AccessedDatabaseInfoViewModel;
import interface_adapter.create_question.QuestionCreationState;
import interface_adapter.create_question.QuestionCreationViewModel;
import use_case.create_quiz.QuizCreationOutputBoundary;
import use_case.create_quiz.QuizCreationOutputData;

/**
 * The presenter for quiz creation.
 */
public class QuizCreationPresenter implements QuizCreationOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel;
    private final QuestionCreationViewModel questionCreationViewModel;

    public QuizCreationPresenter(ViewManagerModel viewManagerModel,
                                 AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel,
                                 QuestionCreationViewModel questionCreationViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.accessedDatabaseInfoViewModel = accessedDatabaseInfoViewModel;
        this.questionCreationViewModel = questionCreationViewModel;
    }

    @Override
    public void prepareSuccessView(QuizCreationOutputData outputData) {
        accessedDatabaseInfoViewModel.firePropertyChanged();

        final QuestionCreationState currentState = questionCreationViewModel.getState();
        currentState.setQuestionsSoFar(null);
        questionCreationViewModel.setState(currentState);

        viewManagerModel.setState(accessedDatabaseInfoViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
