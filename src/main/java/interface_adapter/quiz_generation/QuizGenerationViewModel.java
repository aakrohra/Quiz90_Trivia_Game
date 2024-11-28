package interface_adapter.quiz_generation;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Quiz Generation View.
 */
public class QuizGenerationViewModel extends ViewModel<QuizGenerationState> {

    public static final String TITLE_LABEL = "Quiz Generation";
    public static final String CATEGORY_LABEL = "Select Category: ";
    public static final String QUESTIONS_LABEL = "Select # Of Questions: ";
    public static final String DIFFICULTY_LABEL = "Select Difficulty: ";

    public static final String PLAY_BUTTON_LABEL = "Play";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public QuizGenerationViewModel() {
        super("quiz generation");
        setState(new QuizGenerationState());
    }
}
