package interface_adapter.access_quiz;

import entity.PlayerCreatedQuiz;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.playthrough.PlaythroughState;
import interface_adapter.playthrough.PlaythroughViewModel;
import use_case.access_quiz.AccessQuizOutputBoundary;
import use_case.access_quiz.AccessQuizOutputData;

/**
 * The presenter for the Access Quiz Use Case.
 */
public class AccessQuizPresenter implements AccessQuizOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final AccessedQuizInfoViewModel accessedQuizInfoViewModel;
    private final PlaythroughViewModel playthroughViewModel;

    public AccessQuizPresenter(ViewManagerModel viewManagerModel,
                               LoggedInViewModel loggedInViewModel,
                               AccessedQuizInfoViewModel accessedQuizInfoViewModel,
                               PlaythroughViewModel playthroughViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.accessedQuizInfoViewModel = accessedQuizInfoViewModel;
        this.playthroughViewModel = playthroughViewModel;
    }

    @Override
    public void prepareSuccessView(AccessQuizOutputData response) {
        final AccessedQuizInfoState accessedQuizInfoState = accessedQuizInfoViewModel.getState();

        accessedQuizInfoState.setQuizObject(response.getQuizObject());
        accessedQuizInfoState.setQuizName(response.getQuizName());
        accessedQuizInfoState.setNumQuestions(response.getNumQuestions());
        accessedQuizInfoState.setAuthor(response.getAuthor());

        this.accessedQuizInfoViewModel.setState(accessedQuizInfoState);
        this.accessedQuizInfoViewModel.firePropertyChanged();

        this.viewManagerModel.setState(accessedQuizInfoViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        System.out.println(error + "this is error");
        loggedInState.setQuizKeyError(error);
        System.out.println(loggedInState.getQuizKeyError() + "this is error after setting");
        loggedInViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoggedInView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void playAccessedQuiz(PlayerCreatedQuiz quizObject) {
        final PlaythroughState playthroughState = playthroughViewModel.getState();
        playthroughState.setQuiz(quizObject);
        playthroughViewModel.setState(playthroughState);
        playthroughViewModel.firePropertyChanged();
        viewManagerModel.setState(playthroughViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
