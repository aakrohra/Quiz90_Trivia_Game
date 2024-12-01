package interface_adapter.quiz_generation;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.playthrough.PlaythroughState;
import interface_adapter.playthrough.PlaythroughViewModel;
import use_case.quiz_generation.QuizGenerationOutputBoundary;
import entity.Quiz;
import view.PlaythroughView;

import javax.swing.*;

/**
 * The Presenter for switching to the Quiz Generation View.
 */
public class QuizGenerationPresenter implements QuizGenerationOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final QuizGenerationViewModel quizGenerationViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final PlaythroughViewModel playthroughViewModel;

    public QuizGenerationPresenter(ViewManagerModel viewManagerModel,
                                   QuizGenerationViewModel quizGenerationViewModel,
                                   LoggedInViewModel loggedInViewModel,
                                   PlaythroughViewModel playthroughViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.quizGenerationViewModel = quizGenerationViewModel;
        this.playthroughViewModel = playthroughViewModel;
    }

    /**
     * Prepares the view for the Playthrough screen.
     * Updates the PlaythroughState with the trivia quiz and notifies the view.
     *
     * @param triviaQuiz The quiz to display in the playthrough view.
     */
    @Override
    public void prepareSuccessView(Quiz triviaQuiz) {
        quizGenerationViewModel.getState().setError(null);
        // Update the PlaythroughState with the provided quiz
        final PlaythroughState playthroughState = playthroughViewModel.getState();
        playthroughState.setQuiz(triviaQuiz);
        playthroughViewModel.setState(playthroughState);

        playthroughViewModel.firePropertyChanged();

        // Switch to the Playthrough View
        viewManagerModel.setState(playthroughViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the view to switch to the Quiz Generation screen.
     */
    @Override
    public void switchToQuizGenerationView() {
        // Update the state in the ViewManagerModel
        viewManagerModel.setState(quizGenerationViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the view to switch back to the Main Menu.
     */
    @Override
    public void switchToMainMenuView() {
        // Update the state in the ViewManagerModel
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the fail view.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        quizGenerationViewModel.getState().setError(errorMessage);
        quizGenerationViewModel.firePropertyChanged();
    }
}
