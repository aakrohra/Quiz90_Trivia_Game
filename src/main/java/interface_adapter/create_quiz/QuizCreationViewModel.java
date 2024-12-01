package interface_adapter.create_quiz;

import interface_adapter.ViewModel;

/**
 * The view model for quiz creation.
 */
public class QuizCreationViewModel extends ViewModel<QuizCreationState> {
    public static final String TITLE_LABEL = "Your Quiz!";

    public QuizCreationViewModel() {
        super("quiz creation");
        setState(new QuizCreationState());
    }
}
