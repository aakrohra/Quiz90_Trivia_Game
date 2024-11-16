package interface_adapter.quiz_generation;

import interface_adapter.ViewManagerModel;
import use_case.quiz_generation.QuizGenerationOutputBoundary;

/**
 * The Presenter for switching to the Quiz Generation View.
 */
public class QuizGenerationPresenter implements QuizGenerationOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final QuizGenerationViewModel quizGenerationViewModel;

    public QuizGenerationPresenter(ViewManagerModel viewManagerModel,
                                   QuizGenerationViewModel quizGenerationViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.quizGenerationViewModel = quizGenerationViewModel;
    }

    /**
     * Prepares the view to switch to the Quiz Generation screen.
     */
    @Override
    public void switchToQuizGenerationView() {
        System.out.println("Switching to view");

        // Update the state in the ViewManagerModel
        viewManagerModel.setState(quizGenerationViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
