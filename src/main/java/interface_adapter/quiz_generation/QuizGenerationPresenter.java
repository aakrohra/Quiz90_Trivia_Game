package interface_adapter.quiz_generation;

import interface_adapter.ViewManagerModel;

/**
 * The Presenter for switching to the Quiz Generation View.
 */
public class QuizGenerationPresenter {

    private final ViewManagerModel viewManagerModel;

    public QuizGenerationPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Switch to the Quiz Generation View.
     */
    public void switchToQuizGenerationView() {
        // Tell the View Manager to switch to the QuizGenerationView
        viewManagerModel.setState("quiz_generation");
        viewManagerModel.firePropertyChanged();
    }
}
