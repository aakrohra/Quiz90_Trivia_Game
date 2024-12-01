package interface_adapter.access_database;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_question.QuestionCreationState;
import interface_adapter.create_question.QuestionCreationViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.quiz_generation.QuizGenerationViewModel;
import use_case.access_database.AccessDatabaseOutputBoundary;
import use_case.access_database.AccessDatabaseOutputData;
import view.QuizDatabaseView;
import view.ViewManager;

public class AccessDatabasePresenter implements AccessDatabaseOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final QuestionCreationViewModel questionCreationViewModel;

    public AccessDatabasePresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel,
                                   AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel,
                                   QuestionCreationViewModel questionCreationViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.accessedDatabaseInfoViewModel = accessedDatabaseInfoViewModel;
        this.questionCreationViewModel = questionCreationViewModel;
    }

    @Override
    public void switchToMainMenuView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(AccessDatabaseOutputData accessDatabaseOutputData) {
        System.out.println("prepare successview");
        AccessedDatabaseInfoState accessedDatabaseInfoState = accessedDatabaseInfoViewModel.getState();

        accessedDatabaseInfoState.setDatabase(accessDatabaseOutputData.getQuizDatabase());
        accessedDatabaseInfoState.setUsername(accessDatabaseOutputData.getUsername());

        this.accessedDatabaseInfoViewModel.setState(accessedDatabaseInfoState);
        this.accessedDatabaseInfoViewModel.firePropertyChanged();

        System.out.println("firepropertychange");
        this.viewManagerModel.setState(accessedDatabaseInfoViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToCreateQuestionView(String username) {
        final QuestionCreationState questionCreationState = questionCreationViewModel.getState();
        questionCreationState.setUsername(username);
        questionCreationViewModel.setState(questionCreationState);
        viewManagerModel.setState(questionCreationViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
