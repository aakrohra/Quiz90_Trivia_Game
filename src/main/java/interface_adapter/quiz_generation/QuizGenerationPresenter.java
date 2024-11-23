package interface_adapter.quiz_generation;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.quiz_generation.QuizGenerationOutputBoundary;

/**
 * The Presenter for switching to the Quiz Generation View.
 */
public class QuizGenerationPresenter implements QuizGenerationOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final QuizGenerationViewModel quizGenerationViewModel;
    private final LoggedInViewModel loggedInViewModel;

    public QuizGenerationPresenter(ViewManagerModel viewManagerModel,
                                   QuizGenerationViewModel quizGenerationViewModel,
                                   LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.quizGenerationViewModel = quizGenerationViewModel;
    }

    /**
     * Prepares the view to switch to the Quiz Generation screen.
     */
    @Override
    public void switchToQuizGenerationView() {
        System.out.println("Switching to quiz generation view");

        // Update the state in the ViewManagerModel
        viewManagerModel.setState(quizGenerationViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the view to switch back to the Main Menu.
     */
    @Override
    public void switchToMainMenuView() {
        System.out.println("Switching to main menu view");

        // Update the state in the ViewManagerModel
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
